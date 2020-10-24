package com.a65apps.weather.presentation.realtimeweather

import android.os.Parcelable
import com.a65apps.weather.domain.location.Location
import com.a65apps.weather.domain.weather.RealtimeWeather
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RealtimeWeatherItem(
    val location: Location,
    val weather: RealtimeWeather? = null
) : Parcelable
