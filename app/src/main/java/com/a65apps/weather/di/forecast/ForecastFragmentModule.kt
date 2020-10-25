package com.a65apps.weather.di.forecast

import androidx.lifecycle.ViewModel
import com.a65apps.weather.data.location.LocationRepository
import com.a65apps.weather.data.weather.WeatherRepository
import com.a65apps.weather.di.core.FragmentWithArgsModule
import com.a65apps.weather.di.core.keys.ViewModelKey
import com.a65apps.weather.domain.location.LocationInteractor
import com.a65apps.weather.domain.location.LocationInteractorImpl
import com.a65apps.weather.domain.weather.WeatherInteractor
import com.a65apps.weather.domain.weather.WeatherInteractorImpl
import com.a65apps.weather.presentation.forecast.ForecastFragment
import com.a65apps.weather.presentation.forecast.ForecastParams
import com.a65apps.weather.presentation.forecast.ForecastState
import com.a65apps.weather.presentation.forecast.ForecastViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ForecastFragmentModule.ViewModelModule::class,
    ]
)
class ForecastFragmentModule :
    FragmentWithArgsModule<ForecastFragment, ForecastState, ForecastParams>() {
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
        @ViewModelKey(ForecastViewModel::class)
        fun bindViewModel(viewModel: ForecastViewModel): ViewModel
    }
}
