package com.a65apps.weather.domain.weather

import kotlinx.serialization.Serializable

@Serializable
data class RealtimeWeather(
    val locationId: Long,
    val observationTime: Long,
    val temperature: Float,
    val feelsLike: Float,
    val humidity: Float,
    val weatherCode: String
) : java.io.Serializable
