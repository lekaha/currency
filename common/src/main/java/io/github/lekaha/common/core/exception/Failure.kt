package io.github.lekaha.common.core.exception

sealed class Failure: Throwable() {
    object NetworkConnection : Failure()
    class ServerError(override val cause: Throwable?) : Failure()
    class UnexpectedError(override val cause: Throwable?) : Failure()

    /**
     * Result not found
     */
    object ResultNotFound : Failure()
}