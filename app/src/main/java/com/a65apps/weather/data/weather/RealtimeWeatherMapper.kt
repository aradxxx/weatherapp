package com.a65apps.weather.data.weather

import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.weather.RealtimeWeather

object RealtimeWeatherDtoMapper : Mapper<RealtimeWeatherDto, RealtimeWeatherEntity> {
    override fun map(from: RealtimeWeatherDto): RealtimeWeatherEntity {
        return RealtimeWeatherEntity(
            -1L,
            from.observationTimeDto.date.time,
            from.temperature.value,
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
            from.humidity,
            from.weatherCode
        )
    }
}
