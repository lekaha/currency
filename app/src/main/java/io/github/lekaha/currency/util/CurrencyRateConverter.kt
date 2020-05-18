package io.github.lekaha.currency.util

import io.github.lekaha.currency.entity.CurrencyRateWithCurrency

object CurrencyRateConverter {

    /**
     * Convert the `target` from given `base`'s `toCurrency` as base currency and
     * calculate the new rate by the new base currency.
     *
     * For example,
     * `base` is USD-JPY and `target` is USD-TWD, then convert to JPY-TWD
     */
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