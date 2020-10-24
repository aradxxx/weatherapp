package com.a65apps.weather.presentation.locationsearch

import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.presentation.core.State
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationSearchState(
    val locations: List<Location>,
    val progressVisible: Boolean
) : State
