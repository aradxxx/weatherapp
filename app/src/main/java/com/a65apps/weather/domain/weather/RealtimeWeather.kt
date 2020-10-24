package com.a65apps.weather.domain.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RealtimeWeather(
    val locationId: Long,
    val observationTime: Long,
    val temperature: Float,
    val humidity: Float,
    val weatherCode: String
) : Parcelable
