package com.a65apps.weather.di.realtimedetails

import androidx.lifecycle.ViewModel
import com.a65apps.weather.data.location.LocationRepository
import com.a65apps.weather.data.weather.WeatherRepository
import com.a65apps.weather.di.core.FragmentWithArgsModule
import com.a65apps.weather.di.core.keys.ViewModelKey
import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.domain.location.LocationInteractorImpl
import com.a65apps.weather.domain.weather.WeatherInteractor
import com.a65apps.weather.domain.weather.WeatherInteractorImpl
import com.a65apps.weather.presentation.realtimedetails.RealtimeDetailsFragment
import com.a65apps.weather.presentation.realtimedetails.RealtimeDetailsParams
import com.a65apps.weather.presentation.realtimedetails.RealtimeDetailsState
import com.a65apps.weather.presentation.realtimedetails.RealtimeDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        RealtimeDetailsFragmentModule.ViewModelModule::class,
    ]
)
class RealtimeDetailsFragmentModule :
    FragmentWithArgsModule<RealtimeDetailsFragment, RealtimeDetailsState, RealtimeDetailsParams>() {
    @Provides
    fun provideLocationSearchInteractor(
        locationRepository: LocationRepository
    ): LocationInteractor = LocationInteractorImpl(locationRepository)

    @Provides
    fun provideWeatherInteractor(
        weatherRepository: WeatherRepository
    ): WeatherInteractor = WeatherInteractorImpl(weatherRepository)

    @Module
    interface ViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(RealtimeDetailsViewModel::class)
        fun bindViewModel(viewModel: RealtimeDetailsViewModel): ViewModel
    }
}
