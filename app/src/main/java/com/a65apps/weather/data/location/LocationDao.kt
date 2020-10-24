package com.a65apps.weather.data.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLocations(locations: List<LocationEntity>)

    @Query("SELECT * FROM location ORDER BY name ASC")
    fun subscribeLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM location WHERE location.id == :id")
    fun getLocation(id: Long): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrReplaceLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM location WHERE location.saved ORDER BY name ASC")
    fun getSavedLocations(): List<LocationEntity>
}
