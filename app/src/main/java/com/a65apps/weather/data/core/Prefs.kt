package com.a65apps.weather.data.core

import android.content.Context
import com.a65apps.weather.data.city.PREDEFINED_CITIES
import javax.inject.Inject

private const val NAME = "appPrefs"

private const val DEFAULT_CITY_ID = "DEFAULT_CITY_ID"

class Prefs @Inject constructor(
    private val context: Context
) {
    private val sharedPrefs by lazy { context.getSharedPreferences(NAME, Context.MODE_PRIVATE) }

    var defaultCityId: Long?
        get() {
            val prefValue = sharedPrefs.getLong(
                DEFAULT_CITY_ID,
                PREDEFINED_CITIES.firstOrNull()?.id ?: -1L
            )
            if (prefValue == -1L) {
                return null
            }
            return prefValue
        }
        set(value) {
            val prefValue = value ?: -1
            sharedPrefs.edit().putLong(DEFAULT_CITY_ID, prefValue).apply()
        }
}
