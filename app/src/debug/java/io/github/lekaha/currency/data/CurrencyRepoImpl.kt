package io.github.lekaha.currency.data

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.data.DefaultDataSourceStrategy
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.Currency
import io.github.lekaha.currency.entity.CurrencyRate
import io.github.lekaha.currency.entity.CurrencyRateWithCurrency
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList
import kotlinx.coroutines.delay
import timber.log.Timber

class CurrencyRepoImpl(
    private val currencyLocalCache: CurrencyLocalCache,
    currencyRemoteService: CurrencyRemoteService
) : CurrencyRepo(currencyLocalCache, currencyRemoteService) {

    override fun getSupportedCurrencyList(): Either<Failure, Currencies> =
        object : DefaultDataSourceStrategy<Currencies, Failure>() {
            override fun saveToLocal(data: Currencies) = this@CurrencyRepoImpl.run {
                currencyLocalCache.insertOrUpdateSupportedCurrencies(data)
            }

            override fun shouldFetch(data: Currencies) = data.isEmpty()

            override fun loadFromLocal() = currencyLocalCache.fetchSupportedCurrencies()

            override fun createCall(): Either<Failure, Currencies> = this@CurrencyRepoImpl.run {
                delay(2000)
                Either.Right(
                    listOf(
                        Currency("AED", "United Arab Emirates Dirham"),
                        Currency("AFN", "Afghan Afghani"),
                        Currency("ALL", "Albanian Lek"),
                        Currency("AMD", "Armenian Dram"),
                        Currency("ANG", "Netherlands Antillean Guilder"),
                        Currency("USD", "United State Dollar")
                    )
                )
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

                override fun shouldFetch(data: CurrencyRateWithCurrencyList) = true

                override fun loadFromLocal(): CurrencyRateWithCurrencyList =
                    this@CurrencyRepoImpl.run {
                        currencyLocalCache.fetchCurrencyRateList()
                    }

                override fun createCall(): Either<Failure, CurrencyRateWithCurrencyList> =
                    this@CurrencyRepoImpl.run {
                        delay(500)
                        Either.Right(
                            listOf(
                                CurrencyRateWithCurrency(
                                    CurrencyRate(
                                        "USD",
                                        "AED",
                                        3.673044
                                    ),
                                    Currency("USD", "United State Dollar"),
                                    Currency("AED", "United Arab Emirates Dirham")
                                ),
                                CurrencyRateWithCurrency(
                                    CurrencyRate(
                                        "USD",
                                        "AFN",
                                        76.696429
                                    ),
                                    Currency("USD", "United State Dollar"),
                                    Currency("AFN", "Afghan Afghani")
                                ),
                                CurrencyRateWithCurrency(
                                    CurrencyRate(
                                        "USD",
                                        "ALL",
                                        113.799765
                                    ),
                                    Currency("USD", "United State Dollar"),
                                    Currency("ALL", "Albanian Lek")
                                ),
                                CurrencyRateWithCurrency(
                                    CurrencyRate(
                                        "USD",
                                        "AMD",
                                        487.889823
                                    ),
                                    Currency("USD", "United State Dollar"),
                                    Currency("AMD", "Armenian Dram")
                                ),
                                CurrencyRateWithCurrency(
                                    CurrencyRate(
                                        "USD",
                                        "ANG",
                                        1.794533
                                    ),
                                    Currency("USD", "United State Dollar"),
                                    Currency("ANG", "Netherlands Antillean Guilder")
                                ),
                                CurrencyRateWithCurrency(
                                    CurrencyRate(
                                        "USD",
                                        "USD",
                                        1.0
                                    ),
                                    Currency("USD", "United State Dollar"),
                                    Currency("USD", "United State Dollar")
                                )
                            )
                        )
                    }

                override fun onFetchFailed(failure: Either.Left<Failure>) {
                    Timber.e(failure.a.message)
                }
            }()
        }

}