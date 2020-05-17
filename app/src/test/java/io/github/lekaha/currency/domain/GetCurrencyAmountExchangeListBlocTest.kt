package io.github.lekaha.currency.domain

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.currency.CoroutineTestRule
import io.github.lekaha.currency.TestDataFactory
import io.github.lekaha.currency.TestDataFactory.generateCurrency
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList
import io.github.lekaha.currency.util.CurrencyRateConverter
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.should
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCurrencyAmountExchangeListBlocTest {
    @get: Rule
    val scope = CoroutineTestRule()

    private lateinit var getCurrencyAmountExchangeListBloc: GetCurrencyAmountExchangeListBloc

    @Before
    fun setup() {
        getCurrencyAmountExchangeListBloc =
            GetCurrencyAmountExchangeListBloc(
                GetCurrencyRatesBloc(provideRepo()),
                CurrencyRateConverter
            )
    }

    @Throws(Throwable::class)
    private fun invokeBloc(
        param: GetCurrencyAmountExchangeListBloc.Params,
        block: (CurrencyRateWithCurrencyList) -> Unit = {},
        errorBlock: (Throwable) -> Unit = {}
    ) = scope.launch {
        getCurrencyAmountExchangeListBloc(param, context = scope.coroutineContext)
            .catch { errorBlock(it) }
            .collect { block(it) }
    }

    @Test
    fun `Successfully invoke GetCurrencyRates Bloc`() = runBlockingTest {
        invokeBloc(
            GetCurrencyAmountExchangeListBloc.Params.BaseCurrencyParam(
                generateCurrency("USD")
            ),
            {
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

    @Test
    fun `Base currency not found when invoking GetCurrencyRates Bloc`() = runBlockingTest {
        invokeBloc(
            GetCurrencyAmountExchangeListBloc.Params.BaseCurrencyParam(
                generateCurrency("Test")
            ),
            {}) {
            it.shouldBeInstanceOf(Failure.UnexpectedError::class.java)
            it.cause.shouldBeInstanceOf(IllegalArgumentException::class.java)
            it.cause!!.message.shouldBe("Base currency not found")
        }
    }
}