package com.a65apps.weather.presentation.settings

import android.os.Bundle
import android.view.View
import com.a65apps.weather.R
import com.a65apps.weather.databinding.FragmentSettingsBinding
import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.util.viewBinding

class SettingsFragment : BaseFragment<SettingsViewModel, SettingsState>(
    R.layout.fragment_settings
) {
    private val binding by viewBinding(FragmentSettingsBinding::bind)
    override val vmClass: Class<SettingsViewModel> = SettingsViewModel::class.java

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locations.setOnClickListener {
            viewModel.locationsClicked()
        }
    }
}
