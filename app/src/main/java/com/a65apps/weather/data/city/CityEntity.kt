package com.a65apps.weather.data.city

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val saved: Boolean = false
)
