package com.a65apps.weather.data.weather

import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.network.WeatherApi
import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.weather.RealtimeWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

private const val REALTIME_WEATHER_LIFETIME = 300_000

class WeatherRepositoryImpl @Inject constructor(
    private val appDb: AppDb,
    private val weatherApi: WeatherApi,
    private val realtimeWeatherDtoMapper: Mapper<RealtimeWeatherDto, RealtimeWeatherEntity>,
    private val realtimeWeatherEntityMapper: Mapper<RealtimeWeatherEntity, RealtimeWeather>
) : WeatherRepository {
    override suspend fun updateRealtimeWeather(location: Location) = withContext(Dispatchers.IO) {
        val current = appDb.weatherDao().subscribeRealtimeWeather().first()
            .firstOrNull { it.locationId == location.id }
        if (current == null ||
            System.currentTimeMillis() - current.observationTime >= REALTIME_WEATHER_LIFETIME
        ) {
            updateRealtimeWeatherFromApi(location)
        }
    }

    private suspend fun updateRealtimeWeatherFromApi(location: Location) =
        withContext(Dispatchers.IO) {
            val response = weatherApi.realtime(
                location.coordinates.lat,
                location.coordinates.lon
            ).execute()
            val realtimeWeather = if (response.isSuccessful) {
                response.body()
            } else {
                throw IOException(response.message())
            } ?: throw IOException("empty response")
            appDb.weatherDao().insertRealtimeWeather(
                realtimeWeatherDtoMapper.map(realtimeWeather).copy(
                    locationId = location.id
                )
            )
        }

    override fun subscribeRealtimeWeather(): Flow<List<RealtimeWeather>> {
        return appDb.weatherDao().subscribeRealtimeWeather()
            .map {
                realtimeWeatherEntityMapper.map(it)
            }
    }

    override fun subscribeRealtimeWeather(location: Location): Flow<RealtimeWeather> {
        return appDb.weatherDao().subscribeRealtimeWeather(location.id)
            .map {
                it.firstOrNull()
            }
            .filterNotNull()
            .map {
                realtimeWeatherEntityMapper.map(it)
            }
    }
}
