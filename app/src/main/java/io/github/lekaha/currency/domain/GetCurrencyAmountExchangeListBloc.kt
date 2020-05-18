package io.github.lekaha.currency.domain

import androidx.annotation.VisibleForTesting
import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.domain.Bloc
import io.github.lekaha.currency.entity.Currency
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList
import io.github.lekaha.currency.util.CurrencyRateConverter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlin.coroutines.CoroutineContext

/**
 * Business Logic Component behaves fetching the currency exchange rate list by given the base
 * currency. In this Bloc, not directly connecting the repository it convert the list fetched from
 * [GetCurrencyRatesBloc] with the given base currency (Decorate pattern)
 */
class GetCurrencyAmountExchangeListBloc(
    private val getCurrencyRatesBloc: GetCurrencyRatesBloc,
    private val currencyRateConverter: CurrencyRateConverter
) : Bloc<CurrencyRateWithCurrencyList, GetCurrencyAmountExchangeListBloc.Params>() {

    override fun run(params: Params): Either<Failure, CurrencyRateWithCurrencyList> {
        if (params !is Params.CurrencyRateWithCurrencyListParam) {
            return Either.Left(Failure.UnexpectedError(IllegalArgumentException("Wrong parameter type")))
        }
        return params.list.findLast { it.toCurrency.id == params.base.id }?.let { base ->
            Either.Right(
                params.list.map {
                    currencyRateConverter.convert(base, it)
                }
            )
        }
            ?: Either.Left(Failure.UnexpectedError(IllegalArgumentException("Base currency not found")))
    }

    @UseExperimental(ExperimentalCoroutinesApi::class)
    override fun invoke(params: Params): Flow<CurrencyRateWithCurrencyList> {
        if (params !is Params.BaseCurrencyParam) {
            throw IllegalArgumentException("Wrong parameter type")
        }

        return getCurrencyRatesBloc(false).flatMapLatest { list ->
            super.invoke(Params.CurrencyRateWithCurrencyListParam(params.base, list))
        }
    }

    @VisibleForTesting
    override fun invoke(
        params: Params,
        context: CoroutineContext
    ): Flow<CurrencyRateWithCurrencyList> {
        if (params !is Params.BaseCurrencyParam) {
            throw IllegalArgumentException("Wrong parameter type")
        }

        return getCurrencyRatesBloc(false).flatMapLatest { list ->
            super.invoke(Params.CurrencyRateWithCurrencyListParam(params.base, list), context)
        }
    }

    sealed class Params {
        class BaseCurrencyParam(
            val base: Currency
        ) : Params()

        class CurrencyRateWithCurrencyListParam(
            val base: Currency,
            val list: CurrencyRateWithCurrencyList
        ) : Params()
    }
}