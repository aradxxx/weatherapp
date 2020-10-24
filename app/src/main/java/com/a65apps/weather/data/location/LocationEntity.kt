package com.a65apps.weather.data.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val saved: Boolean = false
)
