package com.a65apps.weather.presentation.forecast

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.R
import com.a65apps.weather.databinding.FragmentForecastBinding
import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.util.viewBinding
import com.a65apps.weather.presentation.util.withInitialArguments
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastFragment : BaseFragment<ForecastViewModel, ForecastState>(
    R.layout.fragment_forecast
) {
    override val vmClass: Class<ForecastViewModel> = ForecastViewModel::class.java
    private val binding by viewBinding(FragmentForecastBinding::bind)
    private val forecastAdapter by lazy {
        ForecastAdapter()
    }

    companion object {
        fun newInstance(params: ForecastParams) = ForecastFragment().withInitialArguments(params)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            viewModel.backPressed()
        }
        binding.weather.setOnRefreshListener {
            viewModel.swipeRefreshed()
        }
        with(binding.weatherRv) {
            layoutManager = LinearLayoutManager(context).apply { recycleChildrenOnDetach = true }
            adapter = forecastAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    override fun updateState(state: ForecastState) {
        when (state) {
            is ForecastState.Init -> {
                renderInitState()
            }
            is ForecastState.LocationForecast -> {
                renderLocationForecast(state)
            }
        }
    }

    private fun renderLocationForecast(state: ForecastState.LocationForecast) {
        weather.isVisible = true
        weather.isRefreshing = false
        forecastAdapter.items = state.forecast
    }

    private fun renderInitState() {
        weather.isVisible = false
    }
}
