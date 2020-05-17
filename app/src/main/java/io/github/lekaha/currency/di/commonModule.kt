package io.github.lekaha.currency.di

import androidx.room.Room
import io.github.lekaha.common.core.http.HttpClient
import io.github.lekaha.currency.BuildConfig
import io.github.lekaha.currency.data.CurrencyDatabase
import io.github.lekaha.currency.data.CurrencyLocalCache
import io.github.lekaha.currency.util.CurrencyRateConverter
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonSerializer
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.ModuleDeclaration

val commonModule: ModuleDeclaration = {
    // http client
    single<Array<Interceptor>> {
        arrayOf(
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.BUILD_TYPE == "release")
                    HttpLoggingInterceptor.Level.NONE
                else
                    HttpLoggingInterceptor.Level.BODY
            }
        )
    }
    factory<JsonSerializer> {
        GsonSerializer()
    }
    factory { (baseUrl: String) ->
        HttpClient(
            provideHttpClient(
                httpClientConfiguration(get(), androidContext().cacheDir),
                jsonFeatureConfiguration(get())
            ), baseUrl
        )
    }

    single<CurrencyDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CurrencyDatabase::class.java,
            "currency_db${if (BuildConfig.BUILD_TYPE == "release") "" else "_debug"}.db"
        ).build()
    }

    // data source
    single {
        CurrencyLocalCache(
            get<CurrencyDatabase>().currencyDao(),
            get<CurrencyDatabase>().currencyRateDao()
        )
    }

    // Utils
    single { CurrencyRateConverter }
}