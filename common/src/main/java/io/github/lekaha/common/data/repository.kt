package io.github.lekaha.common.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

interface Repository
interface LocalRepository : Repository
interface RemoteRepository : Repository

/**
 * The wrapper of [LocalRepository] and [RemoteRepository] to behave a delegate
 */
open class RemoteLocalRepositoryWrapper(
    localRepo: LocalRepository,
    remoteRepo: RemoteRepository
) : LocalRepository by localRepo, RemoteRepository by remoteRepo

/**
 * BaseLocalRepository includes base behaviors
 */
open class BaseLocalRepository : LocalRepository

/**
 * BaseRemoteRepository includes base behaviors
 */
open class BaseRemoteRepository : RemoteRepository

open class RemoteLocalRepository(
    val local: DataSource,
    val remote: DataSource
) : RemoteLocalRepositoryWrapper(BaseLocalRepository(), BaseRemoteRepository()) {
    /**
     * the function wrap the actual coroutine builder
     */
    protected fun <T> run(block: suspend CoroutineScope.() -> T) = runBlocking {
        block(this)
    }
}
