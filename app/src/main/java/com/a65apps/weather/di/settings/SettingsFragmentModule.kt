package com.a65apps.weather.di.settings

import androidx.lifecycle.ViewModel
import com.a65apps.weather.di.core.FragmentModule
import com.a65apps.weather.di.core.keys.ViewModelKey
import com.a65apps.weather.presentation.settings.SettingsFragment
import com.a65apps.weather.presentation.settings.SettingsState
import com.a65apps.weather.presentation.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [
        SettingsFragmentModule.ViewModelModule::class,
    ]
)
class SettingsFragmentModule : FragmentModule<SettingsFragment, SettingsState>() {
    @Module
    interface ViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(SettingsViewModel::class)
        fun bindViewModel(viewModel: SettingsViewModel): ViewModel
    }
}
