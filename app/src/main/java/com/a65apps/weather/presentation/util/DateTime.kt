package com.a65apps.weather.presentation.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

val UTC: TimeZone = TimeZone.getTimeZone("UTC")
const val NIGHT_START = 21
const val NIGHT_END = 6
private const val DATE = "d MMMM"
private const val DAY_NAME = "EEEE"
fun Long.isDay(): Boolean {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@isDay
    }
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    return currentHour in NIGHT_END..NIGHT_START
}

fun Long.formatToDateString(): String {
    val formatter = SimpleDateFormat(DATE, Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.formatToDayName(): String {
    val formatter = SimpleDateFormat(DAY_NAME, Locale.getDefault())
    return formatter.format(Date(this))
}

fun Calendar.toDayStart() = this.apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}
