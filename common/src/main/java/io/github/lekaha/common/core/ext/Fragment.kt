package io.github.lekaha.common.core.ext

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import io.github.lekaha.common.presentation.BaseActivity
import io.github.lekaha.common.presentation.BaseFragment

inline fun FragmentManager.inTransaction(block: FragmentTransaction.() -> FragmentTransaction): Int {
    return beginTransaction().block().commit()
}

fun BaseFragment.pop() {
    if (isAdded) parentFragmentManager.popBackStack()
}

val BaseFragment.viewContainer: View?
    get() = with(activity) {
        if (this is BaseActivity) this.findViewById(fragmentContainerId()) else null

    }

val BaseFragment.appContext: Context get() = activity?.applicationContext!!