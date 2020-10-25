package com.a65apps.weather.presentation.realtimeweather

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.R
import com.a65apps.weather.presentation.util.isDay
import com.a65apps.weather.presentation.util.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_location.locationName
import kotlinx.android.synthetic.main.item_realtime_weather.*

fun realtimeWeatherAdapterDelegate(clickListener: (Int) -> Unit) =
    adapterDelegateLayoutContainer<RealtimeWeatherItem, RealtimeWeatherItem>(R.layout.item_realtime_weather) {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener(position)
            }
        }
        bind {
            locationName.text = item.location.name
            val temp = item.weather?.temperature
            temperature.text = when {
                temp == null -> {
                    getString(R.string.no_data)
                }
                temp <= 0F -> {
                    getString(R.string.temperature_celsius, temp)
                }
                else -> {
                    getString(R.string.temperature_celsius_positive, temp)
                }
            }
            val weatherCode = item.weather?.weatherCode
            if (weatherCode != null) {
                val weatherStateRes =
                    getWeatherStateIcon(weatherCode, System.currentTimeMillis().isDay())
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

class RealtimeWeatherAdapter(clickListener: (Int) -> Unit) :
    AsyncListDifferDelegationAdapter<RealtimeWeatherItem>(diffCallback) {
    init {
        delegatesManager.addDelegate(
            realtimeWeatherAdapterDelegate(clickListener)
        )
    }
}
