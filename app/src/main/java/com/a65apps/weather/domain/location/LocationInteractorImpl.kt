package com.a65apps.weather.domain.location

import com.a65apps.weather.data.core.Prefs
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
    private val prefs: Prefs
) : LocationInteractor {
    private val filter = MutableStateFlow("")

    override suspend fun filter(name: String) = withContext(Dispatchers.Default) {
        filter.value = name
    }

    override fun subsribeLocations(): Flow<List<Location>> {
        return filter.flatMapLatest { filterName ->
            locationRepository.subscribeLocations()
                .map { locations ->
                    locations.filter {
                        it.name.startsWith(filterName, true)
                    }
                }
        }
    }

    override suspend fun locationSelected(location: Location) = withContext(Dispatchers.Default) {
        val toggled = location.copy(saved = !location.saved)
        locationRepository.updateLocation(toggled)
        if (toggled.saved || toggled.id == getDefault()?.id) {
            val nextDefaultLocation = if (toggled.saved) {
                toggled
            } else {
                locationRepository.savedLocations().firstOrNull { it.id != location.id }
            }
            setDefault(nextDefaultLocation)
        }
    }

    override suspend fun getDefault(): Location? {
        return locationRepository.savedLocations().firstOrNull { it.id == prefs.defaultLocationId }
    }

    override fun setDefault(location: Location?) {
        prefs.defaultLocationId = location?.id
    }

    override suspend fun searchLocation(name: String) = withContext(Dispatchers.Default) {
        if (name.isEmpty()) {
            return@withContext
        }
        locationRepository.updateLocations(name)
    }
}
