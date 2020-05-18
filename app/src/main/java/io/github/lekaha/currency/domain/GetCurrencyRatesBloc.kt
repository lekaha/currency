package io.github.lekaha.currency.domain

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.domain.Bloc
import io.github.lekaha.currency.data.CurrencyRepo
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList

/**
 * Business Logic Component behaves fetching the currency exchange rate list which has
 * a fixed base currency. Default is USD.
 */
open class GetCurrencyRatesBloc(
    private val currencyRepo: CurrencyRepo
) : Bloc<CurrencyRateWithCurrencyList, Boolean>() {
    override fun run(isForceUpated: Boolean): Either<Failure, CurrencyRateWithCurrencyList> =
        currencyRepo.getCurrencyRateList(isForceUpated)
}