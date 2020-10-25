package com.a65apps.weather.presentation.realtimedetails

import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.weather.RealtimeWeather
import com.a65apps.weather.presentation.core.State
import kotlinx.android.parcel.Parcelize

sealed class RealtimeDetailsState : State {
    @Parcelize
    object Init : RealtimeDetailsState()

    @Parcelize
    data class WeatherState(
        val location: Location,
        val weather: RealtimeWeather
    ) : RealtimeDetailsState()
}
