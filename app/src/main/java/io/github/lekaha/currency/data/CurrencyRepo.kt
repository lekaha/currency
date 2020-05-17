package io.github.lekaha.currency.data

import io.github.lekaha.common.core.exception.Failure
import io.github.lekaha.common.core.functional.Either
import io.github.lekaha.common.data.RemoteLocalRepository
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList

/**
 * Date Repository for fetching Currency-related data
 */
abstract class CurrencyRepo(
    cache: CurrencyLocalCache,
    remote: CurrencyRemoteService
) : RemoteLocalRepository(cache, remote) {
    abstract fun getSupportedCurrencyList(): Either<Failure, Currencies>
    abstract fun getCurrencyRateList(forceUpdated: Boolean = false):
            Either<Failure, CurrencyRateWithCurrencyList>
}