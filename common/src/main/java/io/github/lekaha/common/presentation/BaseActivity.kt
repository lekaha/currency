package io.github.lekaha.common.presentation

import android.os.Bundle
import android.widget.ProgressBar
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.github.lekaha.common.core.ext.inTransaction

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity {
    constructor(): super()
    constructor(@LayoutRes contentLayoutId: Int): super(contentLayoutId)

    override fun onBackPressed() {
        (supportFragmentManager
            .findFragmentById(fragmentContainerId()) as? BaseFragment)?.onBackPressed()
        super.onBackPressed()
    }

    protected fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: with(supportFragmentManager) {
            fragment()?.let { fragment ->
                inTransaction {
                    add(fragmentContainerId(), fragment)
                }
            }
        }

    @IdRes
    abstract fun fragmentContainerId(): Int

    abstract fun fragment(): Fragment?

    abstract fun progress(): ProgressBar?
}
