package io.github.lekaha.currency.domain

import io.github.lekaha.currency.CoroutineTestRule
import io.github.lekaha.currency.TestDataFactory
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.should
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCurrencyRatesBlocTest {
    @get: Rule
    val scope = CoroutineTestRule()

    private lateinit var getCurrencyRateBloc: GetCurrencyRatesBloc

    @Before
    fun setup() {
        getCurrencyRateBloc =
            GetCurrencyRatesBloc(provideRepo())
    }

    @Throws(Throwable::class)
    private fun invokeBloc(
        param: Boolean,
        block: (CurrencyRateWithCurrencyList) -> Unit = {},
        error: (Throwable) -> Unit = {}
    ) = scope.launch {
        getCurrencyRateBloc(param, context = scope.coroutineContext)
            .catch { error(it) }
            .collect { block(it) }
    }

    @Test
    fun `Successfully invoke GetCurrencyRates Bloc`() = runBlockingTest {
        invokeBloc(false, {
            val currencyRates = TestDataFactory.generateCurrencyRateWithCurrencies()
            it.forEachIndexed { index, c ->
                c.should {
                    c.toCurrency == currencyRates[index].toCurrency &&
                            c.fromCurrency == currencyRates[index].fromCurrency &&
                            c.currencyRate == currencyRates[index].currencyRate
                }
            }
        }) {
            throw it
        }
    }
}