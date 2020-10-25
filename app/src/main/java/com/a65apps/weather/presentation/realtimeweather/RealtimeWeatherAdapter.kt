package com.a65apps.weather.presentation.realtimeweather

import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.R
import com.a65apps.weather.databinding.ItemRealtimeWeatherBinding
import com.a65apps.weather.presentation.util.isDay
import com.a65apps.weather.presentation.util.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun realtimeWeatherAdapterDelegate(clickListener: (Int) -> Unit) =
    adapterDelegateViewBinding<RealtimeWeatherItem, RealtimeWeatherItem, ItemRealtimeWeatherBinding>(
        { layoutInflater, root -> ItemRealtimeWeatherBinding.inflate(layoutInflater, root, false) }
    ) {
        itemView.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener(position)
            }
        }
        bind {
            binding.locationName.text = item.location.name
            val temp = item.weather?.temperature
            binding.temperature.text = when {
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
                    binding.weatherState.setImageResource(weatherStateRes)
                    binding.weatherState.isInvisible = false
                } else {
                    binding.weatherState.isInvisible = true
                }
            } else {
                binding.weatherState.isInvisible = true
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
