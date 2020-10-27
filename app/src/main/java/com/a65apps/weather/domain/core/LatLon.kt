package com.a65apps.weather.domain.core

import kotlinx.serialization.Serializable

private const val LAT_MIN = -90.0
private const val LAT_MAX = 90.0
private const val LON_MIN = -180.0
private const val LON_MAX = 180.0

@Serializable
data class LatLon(
    val lat: Double,
    val lon: Double
) : java.io.Serializable {
    fun isValid() = lat in LAT_MIN..LAT_MAX && lon in LON_MIN..LON_MAX
}
