package com.a65apps.weather.data.weather

import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.weather.Forecast
import com.a65apps.weather.domain.weather.RealtimeWeather

object RealtimeWeatherDtoMapper : Mapper<RealtimeWeatherDto, RealtimeWeatherEntity> {
    override fun map(from: RealtimeWeatherDto): RealtimeWeatherEntity {
        return RealtimeWeatherEntity(
            -1L,
            from.observationTimeDto.date.time,
            from.temperature.value,
            from.feelsLike.value,
            from.humidity.value,
            from.weatherCodeDto.code
        )
    }
}

object RealtimeWeatherEntityMapper : Mapper<RealtimeWeatherEntity, RealtimeWeather> {
    override fun map(from: RealtimeWeatherEntity): RealtimeWeather {
        return RealtimeWeather(
            from.locationId,
            from.observationTime,
            from.temperature,
            from.feelsLike,
            from.humidity,
            from.weatherCode
        )
    }
}

object ForecastDtoMapper : Mapper<ForecastDto, ForecastEntity> {
    override fun map(from: ForecastDto): ForecastEntity {
        return ForecastEntity(
            -1L,
            from.observationTime.date.time,
            -1L,
            from.temperature.firstOrNull { it.minTemp != null }?.minTemp?.value,
            from.temperature.firstOrNull { it.maxTemp != null }?.maxTemp?.value,
            from.weatherCode.code
        )
    }
}

object ForecastEntityMapper : Mapper<ForecastEntity, Forecast> {
    override fun map(from: ForecastEntity): Forecast {
        return Forecast(
            from.date,
            from.minTemperature,
            from.maxTemperature,
            from.weatherCode
        )
    }
}
