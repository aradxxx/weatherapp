package com.a65apps.weather.presentation.realtimeweather

import com.a65apps.weather.R
import com.a65apps.weather.presentation.util.isDay

const val RAIN_HEAVY = "rain_heavy"
const val RAIN = "rain"
const val RAIN_LIGHT = "rain_light"
const val FREEZING_RAIN_HEAVY = "freezing_rain_heavy"
const val FREEZING_RAIN = "freezing_rain"
const val FREEZING_RAIN_LIGHT = "freezing_rain_light"
const val FREEZING_DRIZZLE = "freezing_drizzle"
const val DRIZZLE = "drizzle"
const val ICE_PELLETS_HEAVY = "ice_pellets_heavy"
const val ICE_PELLETS = "ice_pellets"
const val ICE_PELLETS_LIGHT = "ice_pellets_light"
const val SNOW_HEAVY = "snow_heavy"
const val SNOW = "snow"
const val SNOW_LIGHT = "snow_light"
const val FLURRIES = "flurries"
const val TSTORM = "tstorm"
const val FOG_LIGHT = "fog_light"
const val FOG = "fog"
const val CLOUDY = "cloudy"
const val MOSTLY_CLOUDY = "mostly_cloudy"
const val PARTLY_CLOUDY = "partly_cloudy"
const val MOSTLY_CLEAR = "mostly_clear"
const val CLEAR = "clear"

@Suppress("LongMethod", "ComplexMethod")
fun getWeatherStateIcon(code: String, timestamp: Long): Int? {
    return when (code) {
        RAIN_HEAVY -> {
            R.drawable.ic_weather_rain_heavy
        }
        RAIN -> {
            R.drawable.ic_weather_rain
        }
        RAIN_LIGHT -> {
            R.drawable.ic_weather_rain_light
        }
        FREEZING_RAIN_HEAVY -> {
            R.drawable.ic_weather_freezing_rain_heavy
        }
        FREEZING_RAIN -> {
            R.drawable.ic_weather_freezing_rain
        }
        FREEZING_RAIN_LIGHT -> {
            R.drawable.ic_weather_freezing_rain_light
        }
        FREEZING_DRIZZLE -> {
            R.drawable.ic_weather_freezing_drizzle
        }
        DRIZZLE -> {
            R.drawable.ic_weather_drizzle
        }
        ICE_PELLETS_HEAVY -> {
            R.drawable.ic_weather_ice_pellets_heavy
        }
        ICE_PELLETS -> {
            R.drawable.ic_weather_ice_pellets
        }
        ICE_PELLETS_LIGHT -> {
            R.drawable.ic_weather_ice_pellets_light
        }
        SNOW_HEAVY -> {
            R.drawable.ic_weather_snow_heavy
        }
        SNOW -> {
            R.drawable.ic_weather_snow
        }
        SNOW_LIGHT -> {
            R.drawable.ic_weather_snow_light
        }
        FLURRIES -> {
            R.drawable.ic_weather_flurries
        }
        TSTORM -> {
            R.drawable.ic_weather_tstorm
        }
        FOG_LIGHT -> {
            R.drawable.ic_weather_fog_light
        }
        FOG -> {
            R.drawable.ic_weather_fog
        }
        CLOUDY -> {
            R.drawable.ic_weather_cloudy
        }
        MOSTLY_CLOUDY -> {
            R.drawable.ic_weather_mostly_cloudy
        }
        PARTLY_CLOUDY -> {
            if (timestamp.isDay()) {
                R.drawable.ic_weather_partly_cloudy_day
            } else {
                R.drawable.ic_weather_partly_cloudy_night
            }
        }
        MOSTLY_CLEAR -> {
            if (timestamp.isDay()) {
                R.drawable.ic_weather_mostly_clear_day
            } else {
                R.drawable.ic_weather_mostly_clear_night
            }
        }
        CLEAR -> {
            if (timestamp.isDay()) {
                R.drawable.ic_weather_clear_day
            } else {
                R.drawable.ic_weather_clear_night
            }
        }
        else -> {
            null
        }
    }
}
