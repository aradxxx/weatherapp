package com.a65apps.weather.presentation.citysearch

import com.a65apps.weather.domain.city.CityInteractor
import com.a65apps.weather.presentation.core.BaseViewModel
import com.a65apps.weather.presentation.core.navigation.AppRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitySearchViewModel @Inject constructor(
    private val cityInteractor: CityInteractor,
    router: AppRouter
) : BaseViewModel<CitySearchState>(CitySearchState(emptyList(), false), router) {
    init {
        subscribeCities()
    }

    private fun subscribeCities() = vmScope.launch {
        cityInteractor.subsribeCities()
            .flowOn(Dispatchers.IO)
            .collect { cities ->
                updateState {
                    it.copy(cities = cities)
                }
            }
    }

    fun cityClicked(position: Int) = vmScope.launch {
        val city = state.cities.getOrNull(position) ?: return@launch
        cityInteractor.citySelected(city)
    }

    fun filterChanged(filter: String) {
        search(filter)
        updateFilter(filter)
    }

    private fun search(filter: String) = vmScope.launch {
        updateState {
            it.copy(progressVisible = true)
        }
        try {
            cityInteractor.searchCities(filter)
        } finally {
            updateState {
                it.copy(progressVisible = false)
            }
        }
    }

    private fun updateFilter(filter: String) = vmScope.launch {
        cityInteractor.filter(filter)
    }
}
