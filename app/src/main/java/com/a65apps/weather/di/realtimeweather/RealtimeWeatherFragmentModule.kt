package com.a65apps.weather.di.realtimeweather

import androidx.lifecycle.ViewModel
import com.a65apps.weather.data.location.LocationRepository
import com.a65apps.weather.data.weather.WeatherRepository
import com.a65apps.weather.di.core.FragmentModule
import com.a65apps.weather.di.core.keys.ViewModelKey
import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.domain.location.LocationInteractorImpl
import com.a65apps.weather.domain.weather.WeatherInteractor
import com.a65apps.weather.domain.weather.WeatherInteractorImpl
import com.a65apps.weather.presentation.realtimeweather.RealtimeWeatherFragment
import com.a65apps.weather.presentation.realtimeweather.RealtimeWeatherState
import com.a65apps.weather.presentation.realtimeweather.RealtimeWeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        RealtimeWeatherFragmentModule.ViewModelModule::class,
    ]
)
class RealtimeWeatherFragmentModule :
    FragmentModule<RealtimeWeatherFragment, RealtimeWeatherState>() {
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
        @ViewModelKey(RealtimeWeatherViewModel::class)
        fun bindViewModel(viewModel: RealtimeWeatherViewModel): ViewModel
    }
}
