package io.github.lekaha.common.domain

import androidx.annotation.VisibleForTesting
import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlin.coroutines.CoroutineContext

/**
 * Business Logic Component,
 * abstract class for a Use Case (Interactor in terms of Clean Architecture).
 */

@UseExperimental(ExperimentalCoroutinesApi::class)
abstract class Bloc<out Type, in Params> : CoroutineScope where Type : Any {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job

    protected abstract fun run(params: Params): Either<Failure, Type>

    private fun invokeWithContext(
        params: Params = NoneParams() as Params,
        context: CoroutineContext = coroutineContext
    ) = channelFlow<Type> {
        // execute the logic and sending result to the channel,
        // or signal failure completion
        callAsync(context) {
            try {
                run(params).either(::close, ::offer)
            } catch (e: Throwable) {
                close(e)
            }
        }

        awaitClose {
            cancel()
        }
    }

    open operator fun invoke(
        params: Params = NoneParams() as Params
    ) = invokeWithContext(params = params)

    @VisibleForTesting
    open operator fun invoke(
        params: Params = NoneParams() as Params,
        context: CoroutineContext
    ) = invokeWithContext(
        params = params,
        context = context
    )

    /**
     * None of parameter
     */
    class NoneParams
}

private fun <Type: Any, Params> Bloc<Type, Params>.callAsync(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend CoroutineScope.() -> Unit) =
    async(
        context = context,
        block = block
    )
