package io.github.lekaha.currency.data

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.lekaha.currency.data.dao.CurrencyDao
import io.github.lekaha.currency.data.dao.CurrencyRateDao
import io.github.lekaha.currency.entity.Currency
import io.github.lekaha.currency.entity.CurrencyRate

@Database(entities = [Currency::class, CurrencyRate::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun currencyRateDao(): CurrencyRateDao
}