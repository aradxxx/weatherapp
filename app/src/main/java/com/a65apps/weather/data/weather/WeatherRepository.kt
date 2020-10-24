package com.a65apps.weather.data.weather

import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.weather.RealtimeWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun updateRealtimeWeather(location: Location)
    fun subscribeRealtimeWeather(): Flow<List<RealtimeWeather>>
}
