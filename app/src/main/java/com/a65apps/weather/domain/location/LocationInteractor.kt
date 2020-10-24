package com.a65apps.weather.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationInteractor {
    suspend fun filter(name: String)
    suspend fun searchLocation(name: String)
    fun subscribeLocations(): Flow<List<Location>>
    fun subscribeSavedLocations(): Flow<List<Location>>
    suspend fun locationSelected(location: Location)
}
