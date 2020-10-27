package com.a65apps.weather.domain.weather

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val date: Long,
    val minTemperature: Float?,
    val maxTemperature: Float?,
    val weatherCode: String
) : java.io.Serializable
