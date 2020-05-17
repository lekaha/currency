package io.github.lekaha.common.core.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import coil.api.load
import coil.api.loadAny
import coil.request.LoadRequestBuilder

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String, builder: LoadRequestBuilder.() -> Unit = {}) = load(url) {
    crossfade(true)
    builder()
}

fun ImageView.loadAny(data: Any, builder: LoadRequestBuilder.() -> Unit = {}) = loadAny(data) {
    crossfade(true)
    builder()
}

fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: FragmentActivity?) =
    load(url) {
        target(
            onSuccess = {
                setImageDrawable(drawable)
                activity?.supportStartPostponedEnterTransition()
            },
            onError = {
                activity?.supportStartPostponedEnterTransition()
            }
        )
    }
