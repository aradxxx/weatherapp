package com.a65apps.weather.presentation.realtimedetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.a65apps.weather.R
import com.a65apps.weather.databinding.FragmentRealtimeDetailsBinding
import com.a65apps.weather.domain.weather.RealtimeWeather
import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.realtimeweather.getWeatherStateIcon
import com.a65apps.weather.presentation.util.isDay
import com.a65apps.weather.presentation.util.viewBinding
import com.a65apps.weather.presentation.util.withInitialArguments
import kotlin.math.roundToInt

class RealtimeDetailsFragment : BaseFragment<RealtimeDetailsViewModel, RealtimeDetailsState>(
    R.layout.fragment_realtime_details
) {
    private val binding by viewBinding(FragmentRealtimeDetailsBinding::bind)
    override val vmClass: Class<RealtimeDetailsViewModel> = RealtimeDetailsViewModel::class.java

    companion object {
        fun newInstance(params: RealtimeDetailsParams) =
            RealtimeDetailsFragment().withInitialArguments(params)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.backPressed()
        }
        binding.weather.setOnRefreshListener {
            viewModel.swipeRefresh()
        }
        binding.day3Forecast.setOnClickListener {
            viewModel.day3ForecastClicked()
        }
        binding.day7Forecast.setOnClickListener {
            viewModel.day7ForecastClicked()
        }
    }

    override fun updateState(state: RealtimeDetailsState) {
        when (state) {
            is RealtimeDetailsState.Init -> {
                renderInitState()
            }
            is RealtimeDetailsState.WeatherState -> {
                renderWeatherState(state)
            }
        }
    }

    private fun renderWeatherState(state: RealtimeDetailsState.WeatherState) {
        binding.weather.isVisible = true
        binding.weather.isRefreshing = false
        binding.toolbar.title = state.location.name
        binding.feelsLike.text = getString(R.string.feels_like_celsius, state.weather.feelsLike)
        binding.humidity.text = getString(R.string.humidity_percents, state.weather.humidity)
        renderTemp(state.weather)
        getWeatherStateIcon(state.weather.weatherCode, System.currentTimeMillis().isDay())?.let {
            binding.header.weatherCode.setImageResource(it)
        }
    }

    private fun renderTemp(weather: RealtimeWeather) {
        val temperature = weather.temperature.roundToInt()
        binding.header.temperature.text = temperature.toString()
        binding.header.plus.isVisible = temperature > 0
    }

    private fun renderInitState() {
        binding.weather.isVisible = false
    }
}
