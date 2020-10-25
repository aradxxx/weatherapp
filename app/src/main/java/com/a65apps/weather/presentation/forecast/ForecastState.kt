package com.a65apps.weather.presentation.forecast

import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.weather.Forecast
import com.a65apps.weather.presentation.core.State
import kotlinx.android.parcel.Parcelize

sealed class ForecastState : State {
    @Parcelize
    object Init : ForecastState()

    @Parcelize
    data class LocationForecast(
        val location: Location,
        val forecast: List<Forecast>
    ) : ForecastState()
}
