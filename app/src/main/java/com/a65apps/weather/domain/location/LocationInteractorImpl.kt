package com.a65apps.weather.domain.location

import com.a65apps.weather.data.location.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationInteractorImpl @Inject constructor(
    private val locationRepository: LocationRepository,
) : LocationInteractor {
    private val filter = MutableStateFlow("")

    override suspend fun filter(name: String) = withContext(Dispatchers.Default) {
        filter.value = name
    }

    override fun subscribeLocations(): Flow<List<Location>> {
        return filter.flatMapLatest { filterName ->
            locationRepository.subscribeLocations()
                .map { locations ->
                    locations.filter {
                        it.name.startsWith(filterName, true)
                    }
                }
        }
    }

    override fun subscribeSavedLocations(): Flow<List<Location>> {
        return locationRepository.subscribeSavedLocations()
    }

    override suspend fun locationSelected(location: Location) = withContext(Dispatchers.Default) {
        val modifiedLocation = if (location.savedTimestamp == null) {
            location.copy(savedTimestamp = System.currentTimeMillis())
        } else {
            location.copy(savedTimestamp = null)
        }
        locationRepository.updateLocation(modifiedLocation)
    }

    override suspend fun searchLocation(name: String) = withContext(Dispatchers.Default) {
        if (name.isEmpty()) {
            return@withContext
        }
        locationRepository.updateLocations(name)
    }
}
