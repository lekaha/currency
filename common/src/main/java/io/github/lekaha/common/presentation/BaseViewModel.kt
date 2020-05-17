package io.github.lekaha.common.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@UseExperimental(InternalCoroutinesApi::class)
open class BaseViewModel : ViewModel() {
    var failure: MutableLiveData<Throwable> = MutableLiveData()

    fun handleFailure(failure: Throwable) {
        this.failure.value = failure
    }

    @UseExperimental(ExperimentalCoroutinesApi::class)
    protected suspend inline fun <reified T> Flow<T>.collectCatch(liveData: MutableLiveData<T>) {
        catch {
            handleFailure(it)
        }.collect {
            viewModelScope.launch {
                liveData.value = it
            }
        }
    }
}
