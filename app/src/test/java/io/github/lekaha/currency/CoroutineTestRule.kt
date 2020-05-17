package io.github.lekaha.currency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.coroutines.ContinuationInterceptor

/**
 * Test Rule for Kotlin Coroutine.
 * Inspired from https://proandroiddev.com/eliminating-coroutine-leaks-in-tests-3af825e7cde2
 */
class CoroutineTestRule : TestRule,
    TestCoroutineScope by TestCoroutineScope() {
    val dispatcher = coroutineContext[ContinuationInterceptor] as TestCoroutineDispatcher

    override fun apply(
        base: Statement,
        description: Description?
    ) = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(dispatcher)
            try {
                base.evaluate()
            } catch (e: Throwable) {
                throw e
            }
            cleanupTestCoroutines()
            Dispatchers.resetMain()
        }
    }
}