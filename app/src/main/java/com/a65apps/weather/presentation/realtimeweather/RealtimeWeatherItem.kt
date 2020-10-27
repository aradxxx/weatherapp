package com.a65apps.weather.presentation.realtimeweather

import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.weather.RealtimeWeather
import kotlinx.serialization.Serializable

@Serializable
data class RealtimeWeatherItem(
    val location: Location,
    val weather: RealtimeWeather? = null
) : java.io.Serializable
