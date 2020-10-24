package com.a65apps.weather.presentation.citysearch

import com.a65apps.weather.domain.city.City
import com.a65apps.weather.presentation.core.State
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CitySearchState(
    val cities: List<City>,
    val progressVisible: Boolean
) : State
