package com.a65apps.weather.di.citysearch

import androidx.lifecycle.ViewModel
import com.a65apps.weather.data.city.CityRepository
import com.a65apps.weather.data.core.Prefs
import com.a65apps.weather.di.core.FragmentModule
import com.a65apps.weather.di.core.keys.ViewModelKey
import com.a65apps.weather.domain.city.CityInteractor
import com.a65apps.weather.domain.city.CityInteractorImpl
import com.a65apps.weather.presentation.citysearch.CitySearchFragment
import com.a65apps.weather.presentation.citysearch.CitySearchState
import com.a65apps.weather.presentation.citysearch.CitySearchViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module(
    includes = [
        CitySearchFragmentModule.ViewModelModule::class,
    ]
)
class CitySearchFragmentModule : FragmentModule<CitySearchFragment, CitySearchState>() {
    @Provides
    fun provideCitySearchInteractor(cityRepository: CityRepository, prefs: Prefs): CityInteractor =
        CityInteractorImpl(cityRepository, prefs)

    @Module
    interface ViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(CitySearchViewModel::class)
        fun bindViewModel(viewModel: CitySearchViewModel): ViewModel
    }
}
