package com.a65apps.weather.presentation.realtimedetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RealtimeDetailsParams(
    val locationId: Long
) : Parcelable
