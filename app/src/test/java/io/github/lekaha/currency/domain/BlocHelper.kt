package io.github.lekaha.currency.domain

fun provideRepo() =
    MockCurrencyRepo(
        MockCurrencyLocalCache(),
        MockCurrencyRemoteService()
    )