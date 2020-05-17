package io.github.lekaha.currency.domain

import io.github.lekaha.currency.CoroutineTestRule
import io.github.lekaha.currency.TestDataFactory
import io.github.lekaha.currency.entity.Currencies
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.should
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetCurrenciesBlocTest {
    @get: Rule
    val scope = CoroutineTestRule()

    private lateinit var getCurrenciesBloc: GetCurrenciesBloc

    @Before
    fun setup() {
        getCurrenciesBloc =
            GetCurrenciesBloc(provideRepo())
    }

    @Throws(Throwable::class)
    private fun invokeBloc(
        block: (Currencies) -> Unit = {},
        error: (Throwable) -> Unit = {}
    ) = scope.launch {
        getCurrenciesBloc(context = scope.coroutineContext)
            .catch { error(it) }
            .collect { block(it) }
    }

    @Test
    fun `Successfully invoke GetCurrencies Bloc`() = runBlockingTest {
        invokeBloc({
            val currencies = TestDataFactory.generateCurrencies()
            it.forEachIndexed { index, c ->
                c.should {
                    id == currencies[index].id && name == currencies[index].name
                }
            }
        }) {
            throw it
        }
    }
}