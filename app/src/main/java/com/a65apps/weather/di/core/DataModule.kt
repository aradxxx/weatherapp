package com.a65apps.weather.di.core

import android.content.Context
import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.Prefs
import com.a65apps.weather.di.locationsearch.LocationSearchModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        LocationSearchModule::class
    ]
)
class DataModule {
    @Provides
    @Singleton
    fun provideAppDb(context: Context): AppDb = AppDb.getInstance(context)

    @Provides
    @Singleton
    fun providePrefs(context: Context) = Prefs(context)
}
