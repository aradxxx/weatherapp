package com.a65apps.weather.di.core

import android.content.Context
import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.location.LocationDao
import com.a65apps.weather.di.locationsearch.LocationSearchModule
import com.a65apps.weather.di.realtimeweather.WeatherModule
import dagger.Lazy
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
    fun provideAppDb(context: Context, locationDao: Lazy<LocationDao>): AppDb {
        return AppDb.buildDatabase(context, locationDao)
    }

    @Provides
    @Singleton
    fun provideLocationDao(db: AppDb): LocationDao {
        return db.locationDao()
    }
}
