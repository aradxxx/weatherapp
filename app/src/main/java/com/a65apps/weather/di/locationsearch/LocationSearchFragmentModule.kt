package com.a65apps.weather.di.locationsearch

import androidx.lifecycle.ViewModel
import com.a65apps.weather.data.core.Prefs
import com.a65apps.weather.data.location.LocationRepository
import com.a65apps.weather.di.core.FragmentModule
import com.a65apps.weather.di.core.keys.ViewModelKey
import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.domain.location.LocationInteractorImpl
import com.a65apps.weather.presentation.locationsearch.LocationSearchFragment
import com.a65apps.weather.presentation.locationsearch.LocationSearchState
import com.a65apps.weather.presentation.locationsearch.LocationSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        LocationSearchFragmentModule.ViewModelModule::class,
    ]
)
class LocationSearchFragmentModule : FragmentModule<LocationSearchFragment, LocationSearchState>() {
    @Provides
    fun provideLocationSearchInteractor(
        locationRepository: LocationRepository,
        prefs: Prefs
    ): LocationInteractor = LocationInteractorImpl(locationRepository, prefs)

    @Module
    interface ViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(LocationSearchViewModel::class)
        fun bindViewModel(viewModel: LocationSearchViewModel): ViewModel
    }
}
