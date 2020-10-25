package com.a65apps.weather.domain.weather

import com.a65apps.weather.data.weather.WeatherRepository
import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.presentation.util.toDayStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

private const val WEATHER_DAY_START = 6

class WeatherInteractorImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : WeatherInteractor {
    override suspend fun updateRealtimeWeather(location: Location) =
        withContext(Dispatchers.Default) {
            weatherRepository.updateRealtimeWeather(location)
        }

    override fun subscribeRealtimeWeather(): Flow<List<RealtimeWeather>> {
        return weatherRepository.subscribeRealtimeWeather()
    }

    override fun subscribeRealtimeWeather(location: Location): Flow<RealtimeWeather> {
        return weatherRepository.subscribeRealtimeWeather(location)
    }

    override suspend fun updateForecast(location: Location, daysOut: Int) =
        withContext(Dispatchers.Default) {
            val endTime = Calendar.getInstance().apply {
                if (get(Calendar.HOUR_OF_DAY) >= WEATHER_DAY_START) {
                    add(Calendar.DAY_OF_YEAR, daysOut + 1)
                } else {
                    add(Calendar.DAY_OF_YEAR, daysOut)
                }
                toDayStart()
            }.timeInMillis
            weatherRepository.updateForecast(location, endTime)
        }

    override fun subscribeForecast(location: Location, daysOut: Int): Flow<List<Forecast>> {
        val startTime = Calendar.getInstance().apply {
            if (get(Calendar.HOUR_OF_DAY) >= WEATHER_DAY_START) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
            toDayStart()
        }.timeInMillis
        val endTime = Calendar.getInstance().apply {
            if (get(Calendar.HOUR_OF_DAY) < WEATHER_DAY_START) {
                add(Calendar.DAY_OF_YEAR, daysOut - 1)
            } else {
                add(Calendar.DAY_OF_YEAR, daysOut)
            }
            toDayStart()
        }.timeInMillis
        return weatherRepository.subscribeForecast(location, startTime, endTime)
    }
}
