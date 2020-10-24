package com.a65apps.weather.domain.city

import com.a65apps.weather.data.city.CityRepository
import com.a65apps.weather.data.core.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityInteractorImpl @Inject constructor(
    private val cityRepository: CityRepository,
    private val prefs: Prefs
) : CityInteractor {
    private val filter = MutableStateFlow("")

    override suspend fun filter(name: String) = withContext(Dispatchers.Default) {
        filter.value = name
    }

    override fun subsribeCities(): Flow<List<City>> {
        return filter.flatMapLatest { filterName ->
            cityRepository.subscribeCities()
                .map { cities ->
                    cities.filter {
                        it.name.startsWith(filterName, true)
                    }
                }
        }
    }

    override suspend fun citySelected(city: City) = withContext(Dispatchers.Default) {
        val toggled = city.copy(saved = !city.saved)
        cityRepository.updateCity(toggled)
        if (toggled.saved || toggled.id == getDefault()?.id) {
            val nextDefaultCity = if (toggled.saved) {
                toggled
            } else {
                cityRepository.savedCities().firstOrNull { it.id != city.id }
            }
            setDefault(nextDefaultCity)
        }
    }

    override suspend fun getDefault(): City? {
        return cityRepository.savedCities().firstOrNull { it.id == prefs.defaultCityId }
    }

    override fun setDefault(city: City?) {
        prefs.defaultCityId = city?.id
    }

    override suspend fun searchCities(name: String) = withContext(Dispatchers.Default) {
        if (name.isEmpty()) {
            return@withContext
        }
        cityRepository.updateCities(name)
    }
}
