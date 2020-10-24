package com.a65apps.weather.data.location

import com.a65apps.weather.domain.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun updateLocations(name: String)
    fun subscribeLocations(): Flow<List<Location>>
    suspend fun updateLocation(location: Location)
    suspend fun savedLocations(): List<Location>
}
