package com.a65apps.weather.domain.weather

import com.a65apps.weather.domain.location.Location
import kotlinx.coroutines.flow.Flow

interface WeatherInteractor {
    suspend fun updateRealtimeWeather(location: Location)
    fun subscribeRealtimeWeather(): Flow<List<RealtimeWeather>>
    fun subscribeRealtimeWeather(location: Location): Flow<RealtimeWeather>
    suspend fun updateForecast(location: Location, daysOut: Int)
    fun subscribeForecast(location: Location, daysOut: Int): Flow<List<Forecast>>
}
