package com.a65apps.weather.di.realtimeweather

import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.network.WeatherApi
import com.a65apps.weather.data.weather.ForecastDto
import com.a65apps.weather.data.weather.ForecastDtoMapper
import com.a65apps.weather.data.weather.ForecastEntity
import com.a65apps.weather.data.weather.ForecastEntityMapper
import com.a65apps.weather.data.weather.RealtimeWeatherDto
import com.a65apps.weather.data.weather.RealtimeWeatherDtoMapper
import com.a65apps.weather.data.weather.RealtimeWeatherEntity
import com.a65apps.weather.data.weather.RealtimeWeatherEntityMapper
import com.a65apps.weather.data.weather.WeatherRepository
import com.a65apps.weather.data.weather.WeatherRepositoryImpl
import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.weather.Forecast
import com.a65apps.weather.domain.weather.RealtimeWeather
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherModule {
    @Provides
    @Singleton
    fun provideRealtimeWeatherDtoMapper(): Mapper<RealtimeWeatherDto, RealtimeWeatherEntity> =
        RealtimeWeatherDtoMapper

    @Provides
    @Singleton
    fun provideRealtimeWeatherEntityMapper(): Mapper<RealtimeWeatherEntity, RealtimeWeather> =
        RealtimeWeatherEntityMapper

    @Provides
    @Singleton
    fun provideForecastDtoMapper(): Mapper<ForecastDto, ForecastEntity> = ForecastDtoMapper

    @Provides
    @Singleton
    fun provideForecastEntityMapper(): Mapper<ForecastEntity, Forecast> = ForecastEntityMapper

    @Provides
    @Singleton
    fun provideWeatherRepository(
        realtimeWeatherDtoMapper: Mapper<RealtimeWeatherDto, RealtimeWeatherEntity>,
        realtimeWeatherEntityMapper: Mapper<RealtimeWeatherEntity, RealtimeWeather>,
        forecastDtoMapper: Mapper<ForecastDto, ForecastEntity>,
        forecastEntityMapper: Mapper<ForecastEntity, Forecast>,
        weatherApi: WeatherApi,
        appDb: AppDb
    ): WeatherRepository =
        WeatherRepositoryImpl(
            appDb,
            weatherApi,
            realtimeWeatherDtoMapper,
            realtimeWeatherEntityMapper,
            forecastDtoMapper,
            forecastEntityMapper,
        )
}
