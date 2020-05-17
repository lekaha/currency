package io.github.lekaha.common.presentation

import android.content.Context
import io.github.lekaha.common.core.ext.networkInfo

/**
 * Injectable class which returns information about the network connection state.
 */
class NetworkHandler constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting ?: false
}