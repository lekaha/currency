package io.github.lekaha.currency.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.github.lekaha.currency.entity.CurrencyRateWithCurrencyList
import io.github.lekaha.currency.entity.CurrencyRates

@Dao
interface CurrencyRateDao {
    @Query("select * from currency_rate")
    fun selectAll(): CurrencyRates

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(currencyRates: CurrencyRates)

    @Transaction
    @Query("SELECT * FROM currency_rate")
    suspend fun getCurrencyRateWithCurrency(): CurrencyRateWithCurrencyList

    @Transaction
    suspend fun deleteAndInsertAll(currencyRates: CurrencyRates) {
        deleteAll()
        insertAll(currencyRates)
    }

    @Query("DELETE FROM currency_rate")
    suspend fun deleteAll()
}