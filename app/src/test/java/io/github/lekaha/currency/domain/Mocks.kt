package io.github.lekaha.currency.domain

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.core.http.HttpClient
import io.github.lekaha.currency.TestDataFactory
import io.github.lekaha.currency.data.CurrencyLocalCache
import io.github.lekaha.currency.data.CurrencyRemoteService
import io.github.lekaha.currency.data.CurrencyRepo
import io.github.lekaha.currency.data.dao.CurrencyDao
import io.github.lekaha.currency.data.dao.CurrencyRateDao
import io.github.lekaha.currency.di.provideHttpClient
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.Currency
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList
import io.github.lekaha.currency.entity.CurrencyRates

class MockCurrencyDao : CurrencyDao {
    override fun selectAll(): Currencies {
        TODO("Test Purpose")
    }

    override suspend fun insert(currency: Currency) {
        TODO("Test Purpose")
    }

    override suspend fun insertAll(currencies: Currencies) {
        TODO("Test Purpose")
    }

    override suspend fun delete(currency: Currency) {
        TODO("Test Purpose")
    }

    override suspend fun deleteAll() {
        TODO("Test Purpose")
    }
}

class MockCurrencyRateDao : CurrencyRateDao {
    override fun selectAll(): CurrencyRates {
        TODO("Test Purpose")
    }

    override suspend fun insertAll(currencyRates: CurrencyRates) {
        TODO("Test Purpose")
    }

    override suspend fun getCurrencyRateWithCurrency(): CurrencyRateWithCurrencyList {
        TODO("Test Purpose")
    }

    override suspend fun deleteAll() {
        TODO("Test Purpose")
    }
}

class MockCurrencyLocalCache : CurrencyLocalCache(MockCurrencyDao(), MockCurrencyRateDao())
class MockCurrencyRemoteService : CurrencyRemoteService(
    HttpClient(
        provideHttpClient(), "baseUrl"
    )
) {
    override suspend fun getSupportCountryList(): Either<Failure, Currencies> {
        TODO("Test Purpose")
    }

    override suspend fun getCurrencyRateList(): Either<Failure, CurrencyRates> {
        TODO("Test Purpose")
    }
}

class MockCurrencyRepo(
    mockCurrencyLocalCache: MockCurrencyLocalCache,
    mockCurrencyRemoteService: MockCurrencyRemoteService
) : CurrencyRepo(mockCurrencyLocalCache, mockCurrencyRemoteService) {
    override fun getSupportedCurrencyList(): Either<Failure, Currencies> =
        Either.Right(TestDataFactory.generateCurrencies())

    override fun getCurrencyRateList(forceUpdated: Boolean): Either<Failure, CurrencyRateWithCurrencyList> =
        Either.Right(TestDataFactory.generateCurrencyRateWithCurrencies())

}
