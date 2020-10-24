package com.a65apps.weather.presentation.locationsearch

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a65apps.weather.R
import com.a65apps.weather.databinding.FragmentLocationSearchBinding
import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.util.viewBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

private const val FILTER_DEBOUNCE = 250L

class LocationSearchFragment : BaseFragment<LocationSearchViewModel, LocationSearchState>(
    R.layout.fragment_location_search
) {
    companion object {
        fun newInstance() = LocationSearchFragment()
    }

    private val binding by viewBinding(FragmentLocationSearchBinding::bind)
    private val filter = MutableStateFlow("")
    private val locationsAdapter by lazy {
        LocationsAdapter { position ->
            viewModel.locationClicked(position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.locationsRv) {
            layoutManager = LinearLayoutManager(context).apply { recycleChildrenOnDetach = true }
            adapter = locationsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
        binding.locationName.doAfterTextChanged {
            filter.value = it.toString()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            filter.debounce(FILTER_DEBOUNCE)
                .collect { viewModel.filterChanged(it) }
        }
    }

    override fun updateState(state: LocationSearchState) {
        super.updateState(state)
        locationsAdapter.items = state.locations
        binding.progressBarLine.isVisible = state.progressVisible
    }

    override val vmClass = LocationSearchViewModel::class.java
}
