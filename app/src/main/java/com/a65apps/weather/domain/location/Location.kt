package com.a65apps.weather.domain.location

import com.a65apps.weather.domain.core.LatLon
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Long,
    val name: String,
    val coordinates: LatLon,
    val savedTimestamp: Long?
) : java.io.Serializable
