package com.a65apps.weather.presentation.forecast

import android.content.Context
import com.a65apps.weather.R
import com.a65apps.weather.domain.weather.Forecast
import com.a65apps.weather.presentation.realtimeweather.getWeatherStateIcon
import com.a65apps.weather.presentation.util.formatToDateString
import com.a65apps.weather.presentation.util.formatToDayName
import com.a65apps.weather.presentation.util.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_forecast.*

fun forecastAdapterDelegate() =
    adapterDelegateLayoutContainer<Forecast, Forecast>(R.layout.item_forecast) {
        bind {
            date.text = item.date.formatToDateString()
            dayName.text = item.date.formatToDayName()
            temperatureMax.text = context.temperatureString(item.maxTemperature)
            temperatureMin.text = context.temperatureString(item.minTemperature)
            val weatherCodeResId = getWeatherStateIcon(item.weatherCode)
            if (weatherCodeResId != null) {
                weatherState.setImageResource(weatherCodeResId)
            } else {
                weatherState.setImageBitmap(null)
            }
        }
    }

val diffCallback = itemCallback<Forecast>(
    areItemsTheSame = { oldItem, newItem ->
        oldItem.date == newItem.date
    }
)

class ForecastAdapter :
    AsyncListDifferDelegationAdapter<Forecast>(diffCallback) {
    init {
        delegatesManager.addDelegate(forecastAdapterDelegate())
    }
}

private fun Context.temperatureString(temperature: Float?): String {
    return when {
        temperature == null -> {
            getString(R.string.no_data)
        }
        temperature <= 0F -> {
            getString(R.string.temperature_celsius, temperature)
        }
        else -> {
            getString(R.string.temperature_celsius_positive, temperature)
        }
    }
}
