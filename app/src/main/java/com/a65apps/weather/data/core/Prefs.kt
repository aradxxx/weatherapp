package com.a65apps.weather.data.core

import android.content.Context
import com.a65apps.weather.data.location.PREDEFINED_LOCATIONS
import javax.inject.Inject

private const val NAME = "appPrefs"

private const val DEFAULT_LOCATION_ID = "DEFAULT_LOCATION_ID"

class Prefs @Inject constructor(
    private val context: Context
) {
    private val sharedPrefs by lazy { context.getSharedPreferences(NAME, Context.MODE_PRIVATE) }

    var defaultLocationId: Long?
        get() {
            val prefValue = sharedPrefs.getLong(
                DEFAULT_LOCATION_ID,
                PREDEFINED_LOCATIONS.firstOrNull()?.id ?: -1L
            )
            if (prefValue == -1L) {
                return null
            }
            return prefValue
        }
        set(value) {
            val prefValue = value ?: -1
            sharedPrefs.edit().putLong(DEFAULT_LOCATION_ID, prefValue).apply()
        }
}
