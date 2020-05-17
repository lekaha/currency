package io.github.lekaha.currency.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "currency_rate")
data class CurrencyRate(
    @ColumnInfo(name = "from_currency_id") val from: String,
    @ColumnInfo(name = "to_currency_id") val to: String,
    @ColumnInfo(name = "rate") val rate: Double,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

typealias CurrencyRates = List<CurrencyRate>

data class CurrencyRateWithCurrency(
    @Embedded val currencyRate: CurrencyRate,
    @Relation(
        parentColumn = "from_currency_id",
        entityColumn = "id"
    )
    val fromCurrency: Currency,
    @Relation(
        parentColumn = "to_currency_id",
        entityColumn = "id"
    )
    val toCurrency: Currency
)

typealias CurrencyRateWithCurrencyList = List<CurrencyRateWithCurrency>