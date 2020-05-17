package io.github.lekaha.currency.ui.views.dialog

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.github.lekaha.common.core.ext.empty

internal fun AppCompatActivity.showError(
    title: String,
    errorMessage: String,
    cancelable: Boolean = false
) =
    ErrorDialog.create(
        this,
        title = title,
        errorMessage = errorMessage,
        cancelable = cancelable
    ).show()

internal fun Fragment.showError(title: String, errorMessage: String, cancelable: Boolean = false) =
    ErrorDialog.create(
        requireContext(),
        title = title,
        errorMessage = errorMessage,
        cancelable = cancelable
    ).show()

internal class ErrorDialog(context: Context) : AlertDialog(context) {
    companion object {
        internal fun create(
            context: Context,
            title: String = "Error",
            errorMessage: String = String.empty(),
            cancelable: Boolean = false
        ) = Builder(context)
            .setMessage(errorMessage)
            .setTitle(title)
            .setCancelable(cancelable)
            .create()
    }
}
