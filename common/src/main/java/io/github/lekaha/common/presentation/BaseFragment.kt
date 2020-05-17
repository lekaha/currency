package io.github.lekaha.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : Fragment {
    constructor(): super()
    constructor(@LayoutRes contentLayoutId: Int): super(contentLayoutId)

    abstract fun layoutId(): Int?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = super.onCreateView(inflater, container, savedInstanceState)
        ?: run {
            val layoutId = layoutId()
            require(layoutId != null)
            inflater.inflate(layoutId, container, false)
        }

    open fun onBackPressed() {}

    protected fun showProgress() = progressStatus(View.VISIBLE)

    fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) {
            if (this != null && this is BaseActivity) {
                 progress()?.visibility = viewStatus
            }
        }
}
