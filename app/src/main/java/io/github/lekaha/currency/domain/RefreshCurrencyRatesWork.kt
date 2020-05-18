package io.github.lekaha.currency.domain

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.collect
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Periodical Worker to sync up the latest currency exchange rate from Remote service
 */
class RefreshCurrencyRatesWork(
    context: Context,
    params: WorkerParameters,
    private val getCurrencyRatesBloc: GetCurrencyRatesBloc
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        getCurrencyRatesBloc(true).collect()
        return Result.success()
    }

    class Factory : WorkerFactory(), KoinComponent {
        private val getCurrencyRatesBloc: GetCurrencyRatesBloc by inject()
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ) = RefreshCurrencyRatesWork(appContext, workerParameters, getCurrencyRatesBloc)

    }
}