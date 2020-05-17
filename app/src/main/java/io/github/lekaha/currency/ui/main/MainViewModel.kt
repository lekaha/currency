package io.github.lekaha.currency.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import io.github.lekaha.common.core.ext.launch
import io.github.lekaha.common.presentation.BaseViewModel
import io.github.lekaha.currency.domain.GetCurrenciesBloc
import io.github.lekaha.currency.domain.GetCurrencyAmountExchangeListBloc
import io.github.lekaha.currency.entity.Currency
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList

class MainViewModel(
    private val getCurrenciesBloc: GetCurrenciesBloc,
    private val getCurrencyAmountExchangeListBloc: GetCurrencyAmountExchangeListBloc,
    private val sharedPreference: SharedPreferences
) : BaseViewModel() {
    private val selectedCurrency = MutableLiveData<Currency>()

    val currencies = getCurrenciesBloc().asLiveData()
    val amountExchangeList = MediatorLiveData<CurrencyRateWithCurrencyList>().apply {
        addSource(selectedCurrency) { selectedCurrency ->
            launch {
                // Update the currency amount exchange list
                getCurrencyAmountExchangeListBloc(
                    GetCurrencyAmountExchangeListBloc.Params.BaseCurrencyParam(selectedCurrency)
                ).collectCatch(this@apply)
            }
        }
    }

    val amount = MutableLiveData("1")

    fun selectCurrency(currency: Currency) {
        with(sharedPreference.edit()) {
            putString(KEY_SELECTED_CURRENCY, currency.id)
            apply()
        }

        selectedCurrency.value = currency
    }

    fun saveLastAmount(amount: String) {
        with(sharedPreference.edit()) {
            putString(KEY_LAST_AMOUNT, amount)
            apply()
        }
    }

    fun loadLastAmount() = sharedPreference.getString(KEY_LAST_AMOUNT, "1")!!
        .also(amount::setValue)

    fun getSelectedCurrencyId() =
        sharedPreference.getString(
            KEY_SELECTED_CURRENCY,
            DEFAULT_CURRENCY.id
        )

    companion object {
        const val KEY_SELECTED_CURRENCY = "selected_currency"
        const val KEY_LAST_AMOUNT = "last_amount"
        val DEFAULT_CURRENCY = Currency("USD", "United States Dollar")
    }
}
