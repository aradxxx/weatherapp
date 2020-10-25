package com.a65apps.weather.presentation.forecast

import com.a65apps.weather.domain.location.Location
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
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SWIPE_REFRESH_DELAY = 500L

class ForecastViewModel @Inject constructor(
    router: AppRouter,
    private val forecastParams: ForecastParams,
    private val locationInteractor: LocationInteractor,
    private val weatherInteractor: WeatherInteractor
) : BaseViewModel<ForecastState>(
    ForecastState.Init,
    router
) {
    init {
        subscribeForecast()
    }

    fun swipeRefreshed() {
        val currentState = state.nullOr<ForecastState.LocationForecast>() ?: return
        updateForecast(currentState.location)
    }

    private fun subscribeForecast() = vmScope.launch {
        val locations = locationInteractor.subscribeLocations().flowOn(Dispatchers.IO).first()
        val location = locations.first { it.id == forecastParams.locationId }

        updateForecast(location)

        weatherInteractor.subscribeForecast(location, forecastParams.daysOut)
            .flowOn(Dispatchers.IO)
            .collect { forecast ->
                updateState {
                    ForecastState.LocationForecast(location, forecast)
                }
            }
    }

    private fun updateForecast(location: Location) = vmScope.launch {
        updateLocationForecast(location)
        delay(SWIPE_REFRESH_DELAY)
        updateState { it }
    }

    private fun updateLocationForecast(location: Location) = vmScope.launch {
        weatherInteractor.updateForecast(location, forecastParams.daysOut)
    }
}
