package com.a65apps.weather.presentation.citysearch

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.R
import com.a65apps.weather.databinding.FragmentCitySearchBinding
import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.util.viewBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

private const val FILTER_DEBOUNCE = 250L

class CitySearchFragment : BaseFragment<CitySearchViewModel, CitySearchState>(
    R.layout.fragment_city_search
) {
    companion object {
        fun newInstance() = CitySearchFragment()
    }

    private val binding by viewBinding(FragmentCitySearchBinding::bind)
    private val filter = MutableStateFlow("")
    private val citiesAdapter by lazy {
        CitiesAdapter { position ->
            viewModel.cityClicked(position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.citiesRv) {
            layoutManager = LinearLayoutManager(context).apply { recycleChildrenOnDetach = true }
            adapter = citiesAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
        binding.cityName.doAfterTextChanged {
            filter.value = it.toString()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            filter.debounce(FILTER_DEBOUNCE)
                .collect { viewModel.filterChanged(it) }
        }
    }

    override fun updateState(state: CitySearchState) {
        super.updateState(state)
        citiesAdapter.items = state.cities
        binding.progressBarLine.isVisible = state.progressVisible
    }

    override val vmClass = CitySearchViewModel::class.java
}
