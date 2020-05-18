package io.github.lekaha.currency

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import io.github.lekaha.currency.di.appModule
import io.github.lekaha.currency.domain.RefreshCurrencyRatesWork
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit

/**
 * Application
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        // TODO: Setup logging to crash report for release build

        setupDependencyInjection()
        setupWorkManagerJob()
    }

    private fun setupDependencyInjection() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@App)
            modules(appModule)
        }
    }

    /*
    Background Currency Sync up Worker,
    it periodically runs once 12 hours when the network is connected
     */
    private fun setupWorkManagerJob() {
        // initialize WorkManager with a Factory
        val workManagerConfiguration = Configuration.Builder()
            .setWorkerFactory(RefreshCurrencyRatesWork.Factory())
            .build()
        WorkManager.initialize(this, workManagerConfiguration)

        // Use constraints to require the work only run when the device is charging and the
        // network is CONNECTED
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Specify that the work should attempt to run every day
        val work = PeriodicWorkRequestBuilder<RefreshCurrencyRatesWork>(12, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        // Enqueue it work WorkManager, keeping any previously scheduled jobs for the same
        // work.
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                RefreshCurrencyRatesWork::class.java.name,
                ExistingPeriodicWorkPolicy.KEEP,
                work
            )
    }
}