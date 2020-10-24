package com.a65apps.weather.presentation.realtimeweather

import androidx.core.view.isInvisible
import com.a65apps.weather.R
import com.a65apps.weather.presentation.util.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_location.locationName
import kotlinx.android.synthetic.main.item_realtime_weather.*

fun realtimeWeatherAdapterDelegate() =
    adapterDelegateLayoutContainer<RealtimeWeatherItem, RealtimeWeatherItem>(R.layout.item_realtime_weather) {
        bind {
            locationName.text = item.location.name
            val temp = item.weather?.temperature
            temperature.text = if (temp != null) {
                getString(R.string.temperature_celsius, temp)
            } else {
                getString(R.string.no_data)
            }
            val weatherCode = item.weather?.weatherCode
            if (weatherCode != null) {
                val weatherStateRes = getWeatherStateIcon(weatherCode, System.currentTimeMillis())
                if (weatherStateRes != null) {
                    weatherState.setImageResource(weatherStateRes)
                    weatherState.isInvisible = false
                } else {
                    weatherState.isInvisible = true
                }
            } else {
                weatherState.isInvisible = true
            }
        }
    }

val diffCallback = itemCallback<RealtimeWeatherItem>(
    areItemsTheSame = { oldItem, newItem ->
        oldItem.location.id == newItem.location.id
    }
)

class RealtimeWeatherAdapter :
    AsyncListDifferDelegationAdapter<RealtimeWeatherItem>(diffCallback) {
    init {
        delegatesManager.addDelegate(
            realtimeWeatherAdapterDelegate()
        )
    }
}
