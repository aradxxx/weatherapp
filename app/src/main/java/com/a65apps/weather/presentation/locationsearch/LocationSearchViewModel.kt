package com.a65apps.weather.presentation.locationsearch

import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.presentation.core.BaseViewModel
import com.a65apps.weather.presentation.core.navigation.AppRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationSearchViewModel @Inject constructor(
    private val locationInteractor: LocationInteractor,
    router: AppRouter
) : BaseViewModel<LocationSearchState>(LocationSearchState(emptyList(), false), router) {
    init {
        subscribeLocations()
    }

    private fun subscribeLocations() = vmScope.launch {
        locationInteractor.subsribeLocations()
            .flowOn(Dispatchers.IO)
            .collect { locations ->
                updateState {
                    it.copy(locations = locations)
                }
            }
    }

    fun locationClicked(position: Int) = vmScope.launch {
        val location = state.locations.getOrNull(position) ?: return@launch
        locationInteractor.locationSelected(location)
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
            locationInteractor.searchLocation(filter)
        } finally {
            updateState {
                it.copy(progressVisible = false)
            }
        }
    }

    private fun updateFilter(filter: String) = vmScope.launch {
        locationInteractor.filter(filter)
    }
}
