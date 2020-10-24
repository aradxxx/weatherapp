package com.a65apps.weather.di.main

import androidx.lifecycle.ViewModel
import com.a65apps.weather.di.core.ActivityModule
import com.a65apps.weather.di.core.keys.ViewModelKey
import com.a65apps.weather.presentation.main.MainActivity
import com.a65apps.weather.presentation.main.MainState
import com.a65apps.weather.presentation.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [MainActivityModule.ViewModelModule::class])
class MainActivityModule : ActivityModule<MainActivity, MainState>() {
    @Module
    interface ViewModelModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        fun bindViewModel(viewModel: MainViewModel): ViewModel
    }
}
