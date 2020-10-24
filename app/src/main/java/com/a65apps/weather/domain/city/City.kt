package com.a65apps.weather.domain.city

import android.os.Parcelable
import com.a65apps.weather.domain.core.LatLon
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val id: Long,
    val name: String,
    val coordinates: LatLon,
    val saved: Boolean
) : Parcelable
