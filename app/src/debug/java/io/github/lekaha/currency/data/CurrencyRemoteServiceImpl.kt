package io.github.lekaha.currency.data

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.core.http.HttpClient
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.CurrencyRates

class CurrencyRemoteServiceImpl(client: HttpClient) : CurrencyRemoteService(client) {
    override suspend fun getSupportCountryList(): Either<Failure, Currencies> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrencyRateList(): Either<Failure, CurrencyRates> {
        TODO("Not yet implemented")
    }
}