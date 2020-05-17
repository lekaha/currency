package io.github.lekaha.currency.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.github.lekaha.currency.entity.Currencies
import io.github.lekaha.currency.entity.Currency

@Dao
interface CurrencyDao {
    @Query("select * from currency")
    fun selectAll(): Currencies

    @Transaction
    suspend fun deleteAndInsertAll(currencies: Currencies) {
        deleteAll()
        insertAll(currencies)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currency: Currency)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(currencies: Currencies)

    @Delete
    suspend fun delete(currency: Currency)

    @Query("DELETE FROM currency")
    suspend fun deleteAll()
}