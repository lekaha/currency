package io.github.lekaha.common.core.http

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.http.contentType
import io.ktor.util.valuesOf
import java.net.URLEncoder

private const val UTF8 = "UTF-8"

fun Map<String, String>.toQueryString() =
    this.map { (k, v) ->
        "${URLEncoder.encode(k, UTF8)}=${URLEncoder.encode(v, UTF8)}"
    }
        .joinToString("&")

fun <T> T.eitherRight() = Either.Right(this)
fun Failure.eitherLeft() = Either.Left(this)

typealias Configuration<T> = T.() -> Unit

fun HttpRequestBuilder.appendAllOnHeaders(apiHeaders: Map<String, List<String>>) {
    apiHeaders.forEach { entry ->
        headers.appendAll(valuesOf(entry.key, entry.value))
    }
}

class HttpClient(
    val client: io.ktor.client.HttpClient,
    val baseUrl: String
) {
    fun error(cause: Throwable) = Failure.ServerError(cause)

    suspend inline fun <reified T> get(
        endpoint: String,
        params: Map<String, String>,
        apiHeaders: Map<String, List<String>> = emptyMap()
    )
        : Either<Failure, T> =
        try {
            client.get<T>("$baseUrl$endpoint?${params.toQueryString()}") {
                appendAllOnHeaders(apiHeaders)
            }.eitherRight()
        } catch (cause: Throwable) {
            error(cause).eitherLeft()
        }

    suspend inline fun <R, reified T> post(
        endpoint: String,
        body: R,
        contentType: String = ContentType.Application.Json.toString(),
        apiHeaders: Map<String, List<String>> = emptyMap()
    ): Either<Failure, T> where R: Any =
        try {
            client.post<T>("$baseUrl$endpoint") {
                this.body = body
                contentType(ContentType.parse(contentType))
                appendAllOnHeaders(apiHeaders)
            }.eitherRight()
        } catch (cause: Throwable) {
            error(cause).eitherLeft()
        }

    suspend inline fun <R : OutgoingContent, reified T> postOutgoing(
        endpoint: String,
        body: R
    ): Either<Failure, T> =
        try {
            client.post<T>("$baseUrl$endpoint") {
                this.body = body
            }.eitherRight()
        } catch (cause: Throwable) {
            error(cause).eitherLeft()
        }

    suspend inline fun <R, reified T> put(
        endpoint: String,
        body: R,
        contentType: String = ContentType.Application.Json.toString(),
        apiHeaders: Map<String, List<String>> = emptyMap()
    ): Either<Failure, T> where R: Any =
        try {
            client.put<T>("$baseUrl$endpoint") {
                this.body = body
                contentType(ContentType.parse(contentType))
                appendAllOnHeaders(apiHeaders)
            }.eitherRight()
        } catch (cause: Throwable) {
            error(cause).eitherLeft()
        }

    suspend inline fun <R, reified T> delete(
        endpoint: String,
        body: R,
        contentType: String = ContentType.Application.Json.toString(),
        apiHeaders: Map<String, List<String>> = emptyMap()
    ): Either<Failure, T> where R: Any =
        try {
            client.delete<T>("$baseUrl$endpoint") {
                this.body = body
                contentType(ContentType.parse(contentType))
                appendAllOnHeaders(apiHeaders)
            }.eitherRight()
        } catch (cause: Throwable) {
            error(cause).eitherLeft()
        }
}
