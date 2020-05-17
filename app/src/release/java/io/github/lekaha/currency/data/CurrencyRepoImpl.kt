package io.github.lekaha.currency.data

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.core.functional.flatMap
import io.github.lekaha.common.core.functional.map
import io.github.lekaha.common.data.DefaultDataSourceStrategy
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.CurrencyRateWithCurrency
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class CurrencyRepoImpl(
    private val currencyLocalCache: CurrencyLocalCache,
    private val currencyRemoteService: CurrencyRemoteService
) : CurrencyRepo(currencyLocalCache, currencyRemoteService) {

    override fun getSupportedCurrencyList(): Either<Failure, Currencies> =
        object : DefaultDataSourceStrategy<Currencies, Failure>() {
            override fun saveToLocal(data: Currencies) = this@CurrencyRepoImpl.run {
                currencyLocalCache.insertOrUpdateSupportedCurrencies(data)
            }

            override fun shouldFetch(data: Currencies) = data.isEmpty()

            override fun loadFromLocal() = currencyLocalCache.fetchSupportedCurrencies()

            override fun createCall(): Either<Failure, Currencies> = this@CurrencyRepoImpl.run {
                currencyRemoteService.getSupportCountryList()
            }

            override fun onFetchFailed(failure: Either.Left<Failure>) {
                Timber.e(failure.a.message)
            }
        }()

    override fun getCurrencyRateList(forceUpdated: Boolean): Either<Failure, CurrencyRateWithCurrencyList> =
        run {
            object : DefaultDataSourceStrategy<CurrencyRateWithCurrencyList, Failure>() {
                override fun saveToLocal(data: CurrencyRateWithCurrencyList) {
                    this@CurrencyRepoImpl.run {
                        currencyLocalCache.insertOrUpdateCurrencyRateList(
                            data.map { it.currencyRate }
                        )
                    }
                }

                /*
                Should not fetch the latest currency rates unless the local cache is empty.
                Otherwise fetching latest currency relays on the sync up Worker.
                 */
                override fun shouldFetch(data: CurrencyRateWithCurrencyList) =
                    forceUpdated || data.isEmpty()

                override fun loadFromLocal(): CurrencyRateWithCurrencyList =
                    this@CurrencyRepoImpl.run {
                        currencyLocalCache.fetchCurrencyRateList()
                    }

                override fun createCall(): Either<Failure, CurrencyRateWithCurrencyList> =
                    this@CurrencyRepoImpl.run {
                        currencyRemoteService.getSupportCountryList().flatMap { currencies ->
                            runBlocking {
                                currencyRemoteService.getCurrencyRateList().map {
                                    it.map { rate ->
                                        CurrencyRateWithCurrency(
                                            rate,
                                            // Must to have teh currency in supported currency list
                                            currencies.findLast { c -> c.id == rate.from }!!,
                                            currencies.findLast { c -> c.id == rate.to }!!
                                        )
                                    }
                                }
                            }
                        }
                    }

                override fun onFetchFailed(failure: Either.Left<Failure>) {
                    Timber.e(failure.a.message)
                }
            }()
        }

}