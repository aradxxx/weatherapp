package com.a65apps.weather.data.weather

import androidx.room.Entity

@Entity(
    tableName = "forecast",
    primaryKeys = ["locationId", "date"]
)
data class ForecastEntity(
    val locationId: Long,
    val date: Long,
    val receiptTime: Long,
    val minTemperature: Float?,
    val maxTemperature: Float?,
    val weatherCode: String
)
