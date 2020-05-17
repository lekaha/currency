package io.github.lekaha.currency.data

import io.github.lekaha.common.data.LocalDataSource
import io.github.lekaha.currency.data.dao.CurrencyDao
import io.github.lekaha.currency.data.dao.CurrencyRateDao
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.CurrencyRates

/**
 * Interfaces of fetching Currency-related data from Database as a local cache
 */
open class CurrencyLocalCache(
    private val currencyDao: CurrencyDao,
    private val currencyRateDao: CurrencyRateDao
) : LocalDataSource() {

    suspend fun insertOrUpdateSupportedCurrencies(data: Currencies) {
        currencyDao.deleteAndInsertAll(data)
    }

    fun fetchSupportedCurrencies() = currencyDao.selectAll()
    suspend fun fetchCurrencyRateList() = currencyRateDao.getCurrencyRateWithCurrency()
    suspend fun insertOrUpdateCurrencyRateList(data: CurrencyRates) =
        currencyRateDao.insertAll(data)
}