package com.a65apps.weather.presentation.util

import java.util.Calendar

const val NIGHT_START = 21
const val NIGHT_END = 6
fun Long.isDay(): Boolean {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@isDay
    }
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    return currentHour in NIGHT_END..NIGHT_START
}
