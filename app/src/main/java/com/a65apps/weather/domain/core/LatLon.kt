package com.a65apps.weather.domain.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

private const val LAT_MIN = -90.0
private const val LAT_MAX = 90.0
private const val LON_MIN = -180.0
private const val LON_MAX = 180.0

@Parcelize
data class LatLon(
    val lat: Double,
    val lon: Double
) : Parcelable {
    fun isValid() = lat in LAT_MIN..LAT_MAX && lon in LON_MIN..LON_MAX
}
