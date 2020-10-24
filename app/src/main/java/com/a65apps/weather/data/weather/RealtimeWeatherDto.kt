package com.a65apps.weather.data.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class RealtimeWeatherDto(
    @Json(name = "temp")
    val temperature: WeatherValueDto,
    @Json(name = "feels_like")
    val feelsLike: WeatherValueDto,
    @Json(name = "humidity")
    val humidity: WeatherValueDto,
    @Json(name = "weather_code")
    val weatherCodeDto: WeatherCodeDto,
    @Json(name = "observation_time")
    val observationTimeDto: ObservationTimeDto
)

@JsonClass(generateAdapter = true)
data class WeatherValueDto(
    @Json(name = "value")
    val value: Float,
    @Json(name = "units")
    val units: String
)

@JsonClass(generateAdapter = true)
data class ObservationTimeDto(
    @Json(name = "value")
    val date: Date
)

@JsonClass(generateAdapter = true)
data class WeatherCodeDto(
    @Json(name = "value")
    val code: String
)
