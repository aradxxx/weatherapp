package com.a65apps.weather.domain.location

import android.os.Parcelable
import com.a65apps.weather.domain.core.LatLon
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val id: Long,
    val name: String,
    val coordinates: LatLon,
    val saved: Boolean
) : Parcelable
