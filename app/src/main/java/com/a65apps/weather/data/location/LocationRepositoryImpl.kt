package com.a65apps.weather.data.location

import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.network.LocationApi
import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDtoMapper: Mapper<LocationDto, LocationEntity>,
    private val locationEntityMapper: Mapper<LocationEntity, Location>,
    private val locationMapper: Mapper<Location, LocationEntity>,
    private val locationApi: LocationApi,
    private val appDb: AppDb
) : LocationRepository {
    override suspend fun updateLocations(name: String) = withContext(Dispatchers.IO) {
        val response = locationApi.search(name).execute()
        val locations = if (!response.isSuccessful) {
            throw IOException(response.message())
        } else {
            response.body()
        } ?: emptyList()
        val locationEntities = locationDtoMapper.map(locations)
        appDb.locationDao().insertLocations(locationEntities)
        return@withContext
    }

    override fun subscribeLocations(): Flow<List<Location>> {
        return appDb.locationDao().subscribeLocations().map { locationEntityMapper.map(it) }
    }

    override suspend fun updateLocation(location: Location) = withContext(Dispatchers.IO) {
        val locationsEntity = locationMapper.map(location)
        appDb.locationDao().addOrReplaceLocation(locationsEntity)
    }

    override suspend fun savedLocations(): List<Location> {
        return appDb.locationDao().getSavedLocations().map { locationEntityMapper.map(it) }
    }
}
