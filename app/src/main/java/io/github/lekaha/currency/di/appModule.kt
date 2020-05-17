@file:kotlin.jvm.JvmName("BaseModuleKt")

package io.github.lekaha.currency.di

import android.content.Context
import io.github.lekaha.currency.R
import io.github.lekaha.currency.domain.GetCurrenciesBloc
import io.github.lekaha.currency.domain.GetCurrencyAmountExchangeListBloc
import io.github.lekaha.currency.domain.GetCurrencyRatesBloc
import io.github.lekaha.currency.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.ModuleDeclaration

val baseModule: ModuleDeclaration = {

    single {
        androidContext().getSharedPreferences(
            androidContext().getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
    }

    // bloc
    factory { GetCurrenciesBloc(get()) }
    factory { GetCurrencyRatesBloc(get()) }
    factory { GetCurrencyAmountExchangeListBloc(get(), get()) }

    // view model
    viewModel { MainViewModel(get(), get(), get()) }
}