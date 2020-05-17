package io.github.lekaha.common.core.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <T : Any, L : LiveData<T>> LifecycleOwner.observeNonnull(liveData: L, body: (T) -> Unit) {
    observe(liveData) {
        it?.let(body)
    }
}

fun <L : LiveData<Throwable>> LifecycleOwner.failure(liveData: L, body: (Throwable) -> Unit) =
    liveData.observe(this, Observer(body))
