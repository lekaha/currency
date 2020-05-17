package io.github.lekaha.currency.ui.model

import androidx.databinding.BaseObservable
import io.github.lekaha.currency.entity.CurrencyRateWithCurrency

data class CurrencyExchangeItem(
    val profile: CurrencyRateWithCurrency,
    var amount: Double
) : BaseObservable()