package com.a65apps.weather.di

import com.a65apps.weather.di.citysearch.CitySearchFragmentModule
import com.a65apps.weather.presentation.citysearch.CitySearchFragment
import com.a65apps.weather.presentation.core.navigation.tab.TabFragment
import com.a65apps.weather.presentation.core.navigation.tab.TabsContainerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBindingModule {
    @ContributesAndroidInjector
    fun bindTabsContainerFragment(): TabsContainerFragment

    @ContributesAndroidInjector
    fun bindTabFragment(): TabFragment

    @ContributesAndroidInjector(modules = [CitySearchFragmentModule::class])
    fun bindCitySearchFragment(): CitySearchFragment
}
