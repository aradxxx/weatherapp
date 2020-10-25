package com.a65apps.weather.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastDto(
    @Json(name = "observation_time")
    val observationTime: ObservationTimeDto,
    @Json(name = "temp")
    val temperature: List<ForecastTempDto>,
    @Json(name = "weather_code")
    val weatherCode: WeatherCodeDto
)

@JsonClass(generateAdapter = true)
data class ForecastTempDto(
    @Json(name = "min")
    val minTemp: WeatherValueDto?,
    @Json(name = "max")
    val maxTemp: WeatherValueDto?
)
