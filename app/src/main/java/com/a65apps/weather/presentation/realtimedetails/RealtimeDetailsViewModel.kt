package com.a65apps.weather.presentation.realtimedetails

import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.domain.weather.WeatherInteractor
import com.a65apps.weather.presentation.core.BaseViewModel
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.util.nullOr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SWIPE_REFRESH_DELAY = 500L

class RealtimeDetailsViewModel @Inject constructor(
    router: AppRouter,
    private val params: RealtimeDetailsParams,
    private val locationInteractor: LocationInteractor,
    private val weatherInteractor: WeatherInteractor
) : BaseViewModel<RealtimeDetailsState>(
    RealtimeDetailsState.Init,
    router
) {
    init {
        subscribeWeather()
    }

    fun swipeRefresh() {
        updateLocationWeather()
    }

    fun day3ForecastClicked() {
        // no op
    }

    fun day7ForecastClicked() {
        // no op
    }

    private fun subscribeWeather() = vmScope.launch {
        val locations = locationInteractor.subscribeSavedLocations().flowOn(Dispatchers.IO).first()
        val location = locations.first { it.id == params.locationId }

        weatherInteractor.subscribeRealtimeWeather(location)
            .map {
                RealtimeDetailsState.WeatherState(
                    location,
                    it
                )
            }
            .flowOn(Dispatchers.IO)
            .collect { newState ->
                updateState { newState }
            }
    }

    private fun updateLocationWeather() = vmScope.launch {
        val currentState = state.nullOr<RealtimeDetailsState.WeatherState>() ?: return@launch
        weatherInteractor.updateRealtimeWeather(currentState.location)
        delay(SWIPE_REFRESH_DELAY)
        updateState { it }
    }
}
