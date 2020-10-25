package com.a65apps.weather.data.location

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "place_id")
    val id: Long,
    @Json(name = "display_name")
    val name: String,
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lon")
    val lon: Double,
)
