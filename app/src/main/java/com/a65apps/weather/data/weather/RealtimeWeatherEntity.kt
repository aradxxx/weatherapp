package com.a65apps.weather.data.weather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "realtime_weather")
data class RealtimeWeatherEntity(
    @PrimaryKey
    val locationId: Long,
    val observationTime: Long,
    val temperature: Float,
    val humidity: Float,
    val weatherCode: String
)
