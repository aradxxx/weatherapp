package com.a65apps.weather.presentation.realtimeweather

import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.domain.weather.RealtimeWeather
import com.a65apps.weather.domain.weather.WeatherInteractor
import com.a65apps.weather.presentation.core.BaseViewModel
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.core.navigation.Screens
import com.a65apps.weather.presentation.core.navigation.tab.Tab
import com.a65apps.weather.presentation.util.nullOr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class RealtimeWeatherViewModel @Inject constructor(
    router: AppRouter,
    private val locationInteractor: LocationInteractor,
    private val weatherInteractor: WeatherInteractor
) : BaseViewModel<RealtimeWeatherState>(
    RealtimeWeatherState.Init,
    router
) {
    init {
        subscribeData()
    }

    fun searchLocationsClicked() {
        navigateToLocationsSearch()
    }

    fun swipeRefresh() {
        forceUpdateAll()
    }

    private fun subscribeData() = vmScope.launch {
        combine(
            locationInteractor.subscribeSavedLocations()
                .distinctUntilChanged()
                .onEach {
                    checkStateForRealtimeWeather(it)
                },
            weatherInteractor.subscribeRealtimeWeather()
                .distinctUntilChanged()
        ) { locations, weathers ->
            buildState(locations, weathers)
        }
            .flowOn(Dispatchers.IO)
            .collect { newState ->
                updateState { newState }
            }
    }

    private fun buildState(
        locations: List<Location>,
        weathers: List<RealtimeWeather>
    ): RealtimeWeatherState {
        return if (locations.isNotEmpty()) {
            RealtimeWeatherState.LocationWeather(
                realtimeWeather = locations.map { location ->
                    RealtimeWeatherItem(
                        location,
                        weathers.firstOrNull {
                            it.locationId == location.id
                        }
                    )
                }
            )
        } else {
            RealtimeWeatherState.NoLocation
        }
    }

    private fun navigateToLocationsSearch() {
        router.newRootChain(Tab.Settings, Screens.Settings, Screens.LocationSearch)
        router.switchTab(Tab.Settings)
    }

    private fun updateWeatherForLocation(location: Location) = vmScope.launch {
        weatherInteractor.updateRealtimeWeather(location)
    }

    @Suppress("MagicNumber")
    private fun forceUpdateAll() = vmScope.launch {
        val current = state.nullOr<RealtimeWeatherState.LocationWeather>() ?: return@launch
        for (weather in current.realtimeWeather) {
            updateWeatherForLocation(weather.location)
        }
        delay(500)
        updateState { it }
    }

    private fun checkStateForRealtimeWeather(locations: List<Location>) {
        for (location in locations) {
            updateWeatherForLocation(location)
        }
    }
}
