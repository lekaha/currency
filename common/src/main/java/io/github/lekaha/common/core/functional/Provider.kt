package io.github.lekaha.common.core.functional

abstract class Provider<T> {
    var original: T? = null
    abstract fun create(): T
    fun get(): T = original ?: create().apply { original = this }

    fun lazyGet(): Lazy<T> = lazy { get() }
}