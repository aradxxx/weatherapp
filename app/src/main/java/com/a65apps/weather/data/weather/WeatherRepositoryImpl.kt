package com.a65apps.weather.data.weather

import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.network.WeatherApi
import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.weather.Forecast
import com.a65apps.weather.domain.weather.RealtimeWeather
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

private const val REALTIME_WEATHER_LIFETIME = 60_000
private const val FORECAST_WEATHER_LIFETIME = 300_000

class WeatherRepositoryImpl @Inject constructor(
    private val appDb: AppDb,
    private val weatherApi: WeatherApi,
    private val realtimeWeatherDtoMapper: Mapper<RealtimeWeatherDto, RealtimeWeatherEntity>,
    private val realtimeWeatherEntityMapper: Mapper<RealtimeWeatherEntity, RealtimeWeather>,
    private val forecastDtoMapper: Mapper<ForecastDto, ForecastEntity>,
    private val forecastEntityMapper: Mapper<ForecastEntity, Forecast>
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

    override suspend fun updateForecast(location: Location, endTime: Long) =
        withContext(Dispatchers.IO) {
            val forecasts =
                appDb.weatherDao().subscribeForecast(location.id, endTime, endTime)
                    .first()
            val forecast = forecasts.firstOrNull()
            if (forecast == null || System.currentTimeMillis() - forecast.receiptTime >= FORECAST_WEATHER_LIFETIME) {
                updateForecastFromApi(location, endTime)
            }
        }

    override fun subscribeForecast(
        location: Location,
        startTime: Long,
        endTime: Long
    ): Flow<List<Forecast>> {
        return appDb.weatherDao().subscribeForecast(location.id, startTime, endTime).map {
            forecastEntityMapper.map(it)
        }
    }

    private suspend fun updateRealtimeWeatherFromApi(location: Location) =
        withContext(Dispatchers.IO) {
            val realtimeWeatherDto = weatherApi.realtime(
                location.coordinates.lat,
                location.coordinates.lon
            )
            appDb.weatherDao().insertRealtimeWeather(
                realtimeWeatherDtoMapper.map(realtimeWeatherDto).copy(
                    locationId = location.id
                )
            )
        }

    private suspend fun updateForecastFromApi(location: Location, endTime: Long) =
        withContext(Dispatchers.IO) {
            val forecastDto = weatherApi.forecast(
                location.coordinates.lat,
                location.coordinates.lon,
                Date(endTime)
            )
            val now = System.currentTimeMillis()
            appDb.weatherDao().deleteLocationForecastOldestThan(location.id, now)
            appDb.weatherDao().insertForecast(
                forecastDtoMapper.map(forecastDto).map {
                    it.copy(
                        locationId = location.id,
                        receiptTime = now
                    )
                }
            )
        }
}
