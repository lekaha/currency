package io.github.lekaha.currency

import io.github.lekaha.currency.entity.Currency
import io.github.lekaha.currency.entity.CurrencyRate
import io.github.lekaha.currency.entity.CurrencyRateWithCurrency

object TestDataFactory {
    fun generateCurrency(name: String) =
        Currency(name, "Name")

    fun generateCurrencyRateWithCurrency(from: String, to: String) =
        CurrencyRateWithCurrency(
            CurrencyRate(from, to, 3.14),
            Currency(from, "From"),
            Currency(to, "To ")
        )

    fun generateCurrencies() =
        listOf(
            Currency("AED", "United Arab Emirates Dirham"),
            Currency("AFN", "Afghan Afghani"),
            Currency("ALL", "Albanian Lek"),
            Currency("AMD", "Armenian Dram"),
            Currency("ANG", "Netherlands Antillean Guilder"),
            Currency("USD", "United State Dollar")
        )

    fun generateCurrencyRateWithCurrencies() =
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
}