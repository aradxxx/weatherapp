package com.a65apps.weather.presentation.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForecastParams(
    val locationId: Long,
    val daysOut: Int
) : Parcelable
