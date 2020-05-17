package io.github.lekaha.currency.util

import io.github.lekaha.currency.entity.CurrencyRateWithCurrency

object CurrencyRateConverter {
    fun convert(
        base: CurrencyRateWithCurrency,
        target: CurrencyRateWithCurrency
    ): CurrencyRateWithCurrency {
        return target.copy(
            fromCurrency = base.toCurrency,
            currencyRate = target.currencyRate.copy(
                from = base.toCurrency.id,
                rate = target.currencyRate.rate / base.currencyRate.rate
            )
        )
    }
}