package io.github.lekaha.common.presentation

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

open class BaseWebViewClient(
    private val onShouldOverrideUrlLoadingListener: OnShouldOverrideUrlLoadingListener
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest) =
        onShouldOverrideUrlLoadingListener.shouldOverrideUrlLoading(view, request, this)
}

interface OnShouldOverrideUrlLoadingListener {
    fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest, client: WebViewClient): Boolean
}

open class BaseWebChromeClient(
    private val onProgressChangedListener: OnProgressChangedListener
) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        onProgressChangedListener.onProgressChanged(view, newProgress)
    }
}

interface OnProgressChangedListener {
    fun onProgressChanged(view: WebView?, newProgress: Int)
}