package io.github.lekaha.common.data

import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.core.functional.map

interface DataSourceStrategy<Result, Error> {
    fun saveToLocal(data: Result)
    fun shouldFetch(data: Result): Boolean
    fun loadFromLocal(): Result
    fun createCall(): Either<Error, Result>
    fun onFetchFailed(failure: Either.Left<Error>)
}

/**
 * Strategy of decision tree
 *
 * +-----------------+      +-------------+   No
 * |  loadFromLocal  |----->| shouldFetch |---------> Do nothing
 * +-----------------+      +-------------+
 *                                  |
 *                              Yes |
 *                                  |
 *                                  v
 * +-----------------+    +-----------------+  Failed  +-----------------+
 * |  saveToLocal    |<---|   createCall    |--------->|  onFetchFailed  |
 * +-----------------+    +-----------------+          +-----------------+
 */
abstract class DefaultDataSourceStrategy<Result, Error>
    : DataSourceStrategy<Result, Error> where Result : Any {

    lateinit var result: Either<Error, Result>

    private fun call(): Either<Error, Result> =
        createCall().also {
            if (it.isRight) {
                it.map(::saveToLocal)
            } else {
                onFetchFailed(it as Either.Left<Error>)
            }
        }

    open fun run(): Either<Error, Result> {
        return try {
            val cachedResult = loadFromLocal()
            if (shouldFetch(cachedResult)) {
                call()
            } else {
                Either.Right(cachedResult)
            }
        } catch (e: Throwable) {
            call()
        }
    }

    operator fun invoke() = run()
}




