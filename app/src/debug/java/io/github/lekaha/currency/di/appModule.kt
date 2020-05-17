package io.github.lekaha.currency.di

import io.github.lekaha.currency.data.CurrencyRemoteService
import io.github.lekaha.currency.data.CurrencyRemoteServiceImpl
import io.github.lekaha.currency.data.CurrencyRepo
import io.github.lekaha.currency.data.CurrencyRepoImpl
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module(override = true) {
    commonModule(this)

    single<CurrencyRemoteService> {
        CurrencyRemoteServiceImpl(
            get { parametersOf("") }
        )
    }

    // repository
    single<CurrencyRepo> { CurrencyRepoImpl(get(), get()) }

    baseModule(this)
}
