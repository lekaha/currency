package io.github.lekaha.currency.util

import io.github.lekaha.currency.TestDataFactory
import org.amshove.kluent.should
import org.junit.Test

class CurrencyRateConverterTest {

    @Test
    fun `test convert functionality`() {
        val base = TestDataFactory.generateCurrencyRateWithCurrency("USD", "JPY")
        val target = TestDataFactory.generateCurrencyRateWithCurrency("USD", "TWD")
        val actual = CurrencyRateConverter.convert(
            base,
            target
        )

        actual.should {
            fromCurrency.id == "JPY" && toCurrency.id == "TWD" && currencyRate.rate == 1.0
        }
    }
}