package io.github.lekaha.common.data

import io.github.lekaha.common.core.http.HttpClient

interface DataSource

open class LocalDataSource : DataSource

open class RemoteDataSource(protected val httpClient: HttpClient) : DataSource