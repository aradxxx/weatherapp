package com.a65apps.weather.di

import com.a65apps.weather.di.locationsearch.LocationSearchFragmentModule
import com.a65apps.weather.di.realtimeweather.RealtimeWeatherFragmentModule
import com.a65apps.weather.di.settings.SettingsFragmentModule
import com.a65apps.weather.presentation.core.navigation.tab.TabFragment
import com.a65apps.weather.presentation.core.navigation.tab.TabsContainerFragment
import com.a65apps.weather.presentation.locationsearch.LocationSearchFragment
import com.a65apps.weather.presentation.realtimeweather.RealtimeWeatherFragment
import com.a65apps.weather.presentation.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBindingModule {
    @ContributesAndroidInjector
    fun bindTabsContainerFragment(): TabsContainerFragment

    @ContributesAndroidInjector
    fun bindTabFragment(): TabFragment

    @ContributesAndroidInjector(modules = [SettingsFragmentModule::class])
    fun bindSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [LocationSearchFragmentModule::class])
    fun bindLocationSearchFragment(): LocationSearchFragment

    @ContributesAndroidInjector(modules = [RealtimeWeatherFragmentModule::class])
    fun bindRealtimeWeatherFragment(): RealtimeWeatherFragment
}
