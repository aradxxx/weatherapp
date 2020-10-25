package com.a65apps.weather.domain.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast(
    val date: Long,
    val minTemperature: Float?,
    val maxTemperature: Float?,
    val weatherCode: String
) : Parcelable
