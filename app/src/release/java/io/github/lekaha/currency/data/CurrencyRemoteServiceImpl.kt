package io.github.lekaha.currency.data

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.core.functional.flatMap
import io.github.lekaha.common.core.http.HttpClient
import io.github.lekaha.currency.data.response.CurrenciesResponse
import io.github.lekaha.currency.data.response.QuotesResponse
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.Currency
import io.github.lekaha.currency.entity.CurrencyRate
import io.github.lekaha.currency.entity.CurrencyRates

class CurrencyRemoteServiceImpl(
    client: HttpClient,
    private val accessKey: String
) : CurrencyRemoteService(client) {

    private val fixedParameters
        get() = mapOf(
            "access_key" to accessKey
        )

    override suspend fun getSupportCountryList(): Either<Failure, Currencies> =
        httpClient.get<CurrenciesResponse>(EP_LIST, fixedParameters).flatMap {
            if (it.success) {
                checkNotNull(it.currencies)
                Either.Right(
                    it.currencies.map { currency ->
                        Currency(currency.key, currency.value)
                    }
                )
            } else {
                checkNotNull(it.error)
                Either.Left(Failure.ServerError(Error("${it.error["code"]} ${it.error["info"]}")))
            }
        }

    override suspend fun getCurrencyRateList(): Either<Failure, CurrencyRates> =
        httpClient.get<QuotesResponse>(EP_LIVE, fixedParameters).flatMap {
            if (it.success) {
                checkNotNull(it.quotes)
                Either.Right(
                    it.quotes.map { quote ->
                        CurrencyRate(
                            quote.key.substring(0, 3),
                            quote.key.substring(3),
                            quote.value
                        )
                    })
            } else {
                checkNotNull(it.error)
                Either.Left(Failure.ServerError(Error("${it.error["code"]} ${it.error["info"]}")))
            }
        }


    companion object {
        const val EP_LIST = "/list"
        const val EP_LIVE = "/live"
    }
}