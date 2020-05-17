package io.github.lekaha.currency.data

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.core.http.HttpClient
import io.github.lekaha.common.data.RemoteDataSource
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.CurrencyRates

/**
 * Interface of fetching Currency-related data from remote
 */
abstract class CurrencyRemoteService(
    client: HttpClient
) : RemoteDataSource(client) {

    abstract suspend fun getSupportCountryList(): Either<Failure, Currencies>
    abstract suspend fun getCurrencyRateList(): Either<Failure, CurrencyRates>
}