package com.a65apps.weather.domain.weather

import com.a65apps.weather.data.weather.WeatherRepository
import com.a65apps.weather.domain.location.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherInteractor {
    override suspend fun updateRealtimeWeather(location: Location) {
        weatherRepository.updateRealtimeWeather(location)
    }

    override fun subscribeRealtimeWeather(): Flow<List<RealtimeWeather>> {
        return weatherRepository.subscribeRealtimeWeather()
    }

    override fun subscribeRealtimeWeather(location: Location): Flow<RealtimeWeather> {
        return weatherRepository.subscribeRealtimeWeather(location)
    }
}
