package com.a65apps.weather.presentation.realtimedetails

import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.domain.weather.WeatherInteractor
import com.a65apps.weather.presentation.core.BaseViewModel
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.core.navigation.Screens
import com.a65apps.weather.presentation.forecast.ForecastParams
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

    @Suppress("MagicNumber")
    fun day3ForecastClicked() {
        navigateToForecast(3)
    }

    @Suppress("MagicNumber")
    fun day7ForecastClicked() {
        navigateToForecast(7)
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
        updateWeather()
        delay(SWIPE_REFRESH_DELAY)
        updateState { it }
    }

    private fun updateWeather() = vmScope.launch {
        val currentState = state.nullOr<RealtimeDetailsState.WeatherState>() ?: return@launch
        weatherInteractor.updateRealtimeWeather(currentState.location)
    }

    private fun navigateToForecast(daysOut: Int) {
        val currentState = state.nullOr<RealtimeDetailsState.WeatherState>() ?: return
        router.navigateTo(Screens.Forecast(ForecastParams(currentState.location.id, daysOut)))
    }
}
