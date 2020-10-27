package com.a65apps.weather.presentation.forecast

import kotlinx.serialization.Serializable

@Serializable
data class ForecastParams(
    val locationId: Long,
    val daysOut: Int
) : java.io.Serializable
