package com.a65apps.weather.presentation.realtimedetails

import kotlinx.serialization.Serializable

@Serializable
data class RealtimeDetailsParams(
    val locationId: Long
) : java.io.Serializable
