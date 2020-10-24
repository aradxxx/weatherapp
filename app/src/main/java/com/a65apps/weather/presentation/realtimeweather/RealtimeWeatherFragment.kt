package com.a65apps.weather.presentation.realtimeweather

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.R
import com.a65apps.weather.databinding.FragmentRealtimeWeatherBinding
import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.util.viewBinding

class RealtimeWeatherFragment : BaseFragment<RealtimeWeatherViewModel, RealtimeWeatherState>(
    R.layout.fragment_realtime_weather
) {
    private val binding by viewBinding(FragmentRealtimeWeatherBinding::bind)
    override val vmClass: Class<RealtimeWeatherViewModel> = RealtimeWeatherViewModel::class.java
    private val realtimeWeatherAdapter by lazy {
        RealtimeWeatherAdapter()
    }

    companion object {
        fun newInstance() = RealtimeWeatherFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.weatherRv) {
            layoutManager = LinearLayoutManager(context).apply { recycleChildrenOnDetach = true }
            adapter = realtimeWeatherAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
        binding.locationSearch.setOnClickListener {
            viewModel.searchLocationsClicked()
        }
        binding.weather.setOnRefreshListener {
            viewModel.swipeRefresh()
        }
    }

    override fun updateState(state: RealtimeWeatherState) {
        super.updateState(state)
        when (state) {
            is RealtimeWeatherState.Init -> {
                renderInit()
            }
            is RealtimeWeatherState.LocationWeather -> {
                renderWeather(state)
            }
            is RealtimeWeatherState.NoLocation -> {
                renderNoLocation()
            }
        }
    }

    private fun renderWeather(state: RealtimeWeatherState.LocationWeather) {
        binding.weather.isVisible = true
        binding.weather.isRefreshing = false
        binding.noLocation.isVisible = false
        realtimeWeatherAdapter.items = state.realtimeWeather
    }

    private fun renderNoLocation() {
        binding.weather.isVisible = false
        binding.noLocation.isVisible = true
    }

    private fun renderInit() {
        binding.noLocation.isVisible = false
        binding.weather.isVisible = false
    }
}
