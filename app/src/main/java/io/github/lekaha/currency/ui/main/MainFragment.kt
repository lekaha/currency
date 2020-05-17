package io.github.lekaha.currency.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.lekaha.common.core.ext.failure
import io.github.lekaha.common.core.ext.observeNonnull
import io.github.lekaha.common.presentation.BaseFragment
import io.github.lekaha.currency.R
import io.github.lekaha.currency.databinding.MainFragmentBinding
import io.github.lekaha.currency.ui.model.CurrencyExchangeItem
import io.github.lekaha.currency.ui.views.dialog.showError
import kotlinx.android.synthetic.main.main_fragment.amount
import kotlinx.android.synthetic.main.main_fragment.currencyExchangeList
import kotlinx.android.synthetic.main.main_fragment.currencySpinner
import org.koin.androidx.viewmodel.ext.android.viewModel

// Top-level MainFragment factory method
fun mainFragmentOf() = MainFragment.newInstance()

class MainFragment : BaseFragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()

    private var exchangeItems = listOf<CurrencyExchangeItem>()

    override fun layoutId() = R.layout.main_fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding = DataBindingUtil.inflate<MainFragmentBinding>(
            inflater,
            R.layout.main_fragment,
            container,
            false
        )
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupLiveData()
    }

    private fun setupViews() {
        showProgress()
        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                showProgress()
                viewModel.currencies.value?.get(position)?.apply(viewModel::selectCurrency)
            }
        }

        currencyExchangeList.itemAnimator = DefaultItemAnimator()
        currencyExchangeList.layoutManager = LinearLayoutManager(
            currencyExchangeList.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        currencyExchangeList.adapter = CurrencyExchangeAdapter()
    }

    private fun setupLiveData() {
        observeNonnull(viewModel.currencies) {
            it.findLast { currency ->
                currency.id == viewModel.getSelectedCurrencyId()
            }?.apply(viewModel::selectCurrency)

            currencySpinner.adapter = ArrayAdapter(
                currencySpinner.context,
                R.layout.layout_currency_spinner_item,
                R.id.currencyId,
                it.map { currency -> currency.id }
            )
        }
        observeNonnull(viewModel.amountExchangeList) {
            exchangeItems = it.map { item ->
                CurrencyExchangeItem(
                    item,
                    (viewModel.amount.value?.toDouble() ?: 1.0) * item.currencyRate.rate
                )
            }
            (currencyExchangeList.adapter as CurrencyExchangeAdapter)
                .setData(exchangeItems)
            hideProgress()
        }
        observeNonnull(viewModel.amount) {
            (currencyExchangeList.adapter as CurrencyExchangeAdapter)
                .setData(
                    exchangeItems.map {
                        CurrencyExchangeItem(
                            it.profile,
                            (viewModel.amount.value?.toDouble()
                                ?: 1.0) * it.profile.currencyRate.rate
                        )
                    }
                )
        }
        failure(viewModel.failure) {
            showError("Error", it.message.orEmpty())
        }

        viewModel.loadLastAmount()
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveLastAmount(amount = amount.text.toString())
    }
}
