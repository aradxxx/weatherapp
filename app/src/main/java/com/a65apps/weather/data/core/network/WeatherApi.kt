package com.a65apps.weather.data.core.network

import com.a65apps.weather.data.weather.ForecastDto
import com.a65apps.weather.data.weather.RealtimeWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

private const val UNIT_SYSTEM_SI = "si"
private const val WEATHER_FIELDS = "temp,feels_like,humidity,weather_code"
private const val FORECAST_FIELDS = "temp,weather_code"

interface WeatherApi {
    @GET("weather/realtime?fields=$WEATHER_FIELDS")
    suspend fun realtime(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("unit_system") unitSystem: String = UNIT_SYSTEM_SI
    ): RealtimeWeatherDto

    @GET("weather/forecast/daily?fields=$FORECAST_FIELDS")
    suspend fun forecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("end_time") endTime: Date,
        @Query("start_time") startTime: String = "now",
        @Query("unit_system") unitSystem: String = UNIT_SYSTEM_SI,
    ): List<ForecastDto>
}
