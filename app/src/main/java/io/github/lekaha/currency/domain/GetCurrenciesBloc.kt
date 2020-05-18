package io.github.lekaha.currency.domain

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.domain.Bloc
import io.github.lekaha.currency.data.CurrencyRepo
import io.github.lekaha.currency.entity.Currencies

/**
 * Business Logic Component behaves fetching the supported currencies
 */
class GetCurrenciesBloc(
    private val currencyRepo: CurrencyRepo
) : Bloc<Currencies, Bloc.NoneParams>() {
    override fun run(params: Bloc.NoneParams): Either<Failure, Currencies> =
        currencyRepo.getSupportedCurrencyList()
}