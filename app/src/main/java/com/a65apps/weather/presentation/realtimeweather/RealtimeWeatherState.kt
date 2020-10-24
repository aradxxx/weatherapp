package com.a65apps.weather.presentation.realtimeweather

import com.a65apps.weather.presentation.core.State
import kotlinx.android.parcel.Parcelize

sealed class RealtimeWeatherState : State {
    @Parcelize
    object Init : RealtimeWeatherState()

    @Parcelize
    object NoLocation : RealtimeWeatherState()

    @Parcelize
    data class LocationWeather(
        val realtimeWeather: List<RealtimeWeatherItem>
    ) : RealtimeWeatherState()
}
