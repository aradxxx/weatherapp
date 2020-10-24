package com.a65apps.weather.di

import com.a65apps.weather.di.main.MainActivityModule
import com.a65apps.weather.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindingModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentBindingModule::class])
    fun bindMainActivity(): MainActivity
}
