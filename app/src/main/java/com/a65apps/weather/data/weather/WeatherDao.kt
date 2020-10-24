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
}
