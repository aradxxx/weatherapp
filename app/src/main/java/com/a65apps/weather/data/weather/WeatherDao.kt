package com.a65apps.weather.data.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRealtimeWeather(realtimeWeatherEntity: RealtimeWeatherEntity)

    @Query("SELECT * FROM realtime_weather")
    fun subscribeRealtimeWeather(): Flow<List<RealtimeWeatherEntity>>

    @Query("SELECT * FROM realtime_weather WHERE locationId == :locationId")
    fun subscribeRealtimeWeather(locationId: Long): Flow<List<RealtimeWeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecast(forecast: List<ForecastEntity>)

    @Query("DELETE FROM forecast WHERE receiptTime < :date")
    fun deleteForecastOldestThan(date: Long)

    @Query("SELECT * FROM forecast WHERE locationId == :locationId AND date >= :startTime AND date <= :endTime")
    fun subscribeForecast(
        locationId: Long,
        startTime: Long,
        endTime: Long
    ): Flow<List<ForecastEntity>>
}
