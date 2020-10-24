package com.a65apps.weather.data.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCities(cities: List<CityEntity>)

    @Query("SELECT * FROM city ORDER BY name ASC")
    fun subscribeCities(): Flow<List<CityEntity>>

    @Query("SELECT * FROM city WHERE city.id == :id")
    fun getCity(id: Long): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(cityEntity: CityEntity)

    @Query("SELECT * FROM city WHERE city.saved ORDER BY name ASC")
    fun getSavedCities(): List<CityEntity>
}
