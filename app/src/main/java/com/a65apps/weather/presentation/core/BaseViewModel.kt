package com.a65apps.weather.presentation.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a65apps.weather.presentation.core.lifecycle.LiveEvent
import com.a65apps.weather.presentation.core.navigation.AppRouter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.plus
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference

abstract class BaseViewModel<S : State>(
    initialState: S,
    protected val router: AppRouter
) : ViewModel() {
    private val stateLiveData = MutableLiveData(initialState)
    private val eventLiveData = LiveEvent<Any?>()
    private val atomicState = AtomicReference<S>(initialState)
    protected val state: S get() = atomicState.get()
    private val eHandler = CoroutineExceptionHandler { _, e ->
        handleException(e)
    }
    protected val vmScope = viewModelScope + eHandler

    protected open fun handleException(e: Throwable) {
        if (e is CancellationException) {
            return
        }
        Timber.e(e)
    }

    open fun stateLiveData(): LiveData<S> = stateLiveData
    open fun eventLiveData(): LiveData<Any?> = eventLiveData

    protected fun updateState(function: (S) -> S) {
        var currentState: S
        var updatedState: S
        do {
            currentState = state
            updatedState = function(currentState)
        } while (!atomicState.compareAndSet(currentState, updatedState))
        stateLiveData.postValue(updatedState)
    }

    protected fun postEvent(event: Any?) {
        eventLiveData.postValue(event)
    }

    open fun backPressed() {
        router.exit()
    }
}