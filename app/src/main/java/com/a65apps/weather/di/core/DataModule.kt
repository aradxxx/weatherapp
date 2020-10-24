package com.a65apps.weather.di.core

import android.content.Context
import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.di.locationsearch.LocationSearchModule
import com.a65apps.weather.di.realtimeweather.WeatherModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        LocationSearchModule::class,
        WeatherModule::class
    ]
)
class DataModule {
    @Provides
    @Singleton
    fun provideAppDb(context: Context): AppDb = AppDb.getInstance(context)
}
