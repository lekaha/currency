package io.github.lekaha.currency

import android.os.Bundle
import io.github.lekaha.common.presentation.BaseActivity
import io.github.lekaha.currency.ui.main.mainFragmentOf
import kotlinx.android.synthetic.main.main_activity.activityProgressBar

class MainActivity : BaseActivity(R.layout.main_activity) {
    override fun fragmentContainerId() = R.id.container
    override fun fragment() = mainFragmentOf()
    override fun progress() = activityProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(savedInstanceState)
    }
}
