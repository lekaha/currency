package io.github.lekaha.currency.di

import io.github.lekaha.common.core.ext.empty
import io.github.lekaha.common.core.http.Configuration
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.UserAgent
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.JsonSerializer
import io.ktor.http.HttpMethod
import io.ktor.http.userAgent
import okhttp3.Cache
import okhttp3.Interceptor
import java.io.File
import java.util.concurrent.TimeUnit

fun jsonFeatureConfiguration(
    jsonSerializer: JsonSerializer
): Configuration<JsonFeature.Config> = {
    serializer = jsonSerializer
}

fun httpClientConfiguration(
    interceptors: Array<Interceptor>,
    cacheFile: File? = null
): Configuration<OkHttpConfig> = {

    interceptors.forEach(::addInterceptor)
    config {
        // this: OkHttpClient.Builder ->

        // Reference: https://square.github.io/okhttp/recipes/#response-caching-kt-java
        if (cacheFile != null) {
            val httpCacheDirectory = File(cacheFile, "http-cache")
            cache(Cache(httpCacheDirectory, 10 * 1024 * 1024))
        }
        followRedirects(true)
        connectTimeout(2, TimeUnit.SECONDS)
        writeTimeout(5, TimeUnit.SECONDS)
        readTimeout(5, TimeUnit.SECONDS)
    }
}

fun provideHttpClient(
    cfgs: Configuration<OkHttpConfig> = {},
    jsonCfgs: Configuration<JsonFeature.Config> = {},
    userAgent: String = String.empty()
): io.ktor.client.HttpClient = io.ktor.client.HttpClient(OkHttp) {
    engine {
        cfgs(this)
    }
    install(JsonFeature) {
        jsonCfgs(this)
    }
    install(DefaultRequest) {
        headers.append("Accept", "application/json;")
        if (userAgent.isEmpty()) {
            install(UserAgent.Feature)
        } else {
            userAgent(userAgent)
        }
    }
}
