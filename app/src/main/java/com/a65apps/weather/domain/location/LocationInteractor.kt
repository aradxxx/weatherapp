package com.a65apps.weather.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationInteractor {
    suspend fun filter(name: String)
    suspend fun searchLocation(name: String)
    fun subsribeLocations(): Flow<List<Location>>
    suspend fun locationSelected(location: Location)
    suspend fun getDefault(): Location?
    fun setDefault(location: Location?)
}
