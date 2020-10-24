package com.a65apps.weather.di.locationsearch

import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.network.LocationApi
import com.a65apps.weather.data.location.LocationDto
import com.a65apps.weather.data.location.LocationDtoMapper
import com.a65apps.weather.data.location.LocationEntity
import com.a65apps.weather.data.location.LocationEntityMapper
import com.a65apps.weather.data.location.LocationMapper
import com.a65apps.weather.data.location.LocationRepository
import com.a65apps.weather.data.location.LocationRepositoryImpl
import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.location.Location
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationSearchModule {
    @Provides
    @Singleton
    fun provideLocationEntityMapper(): Mapper<LocationEntity, Location> = LocationEntityMapper

    @Provides
    @Singleton
    fun provideLocationDtoMapper(): Mapper<LocationDto, LocationEntity> = LocationDtoMapper

    @Provides
    @Singleton
    fun provideLocationMapper(): Mapper<Location, LocationEntity> = LocationMapper

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationDtoMapper: Mapper<LocationDto, LocationEntity>,
        locationEntityMapper: Mapper<LocationEntity, Location>,
        locationMapper: Mapper<Location, LocationEntity>,
        locationApi: LocationApi,
        appDb: AppDb
    ): LocationRepository =
        LocationRepositoryImpl(
            locationDtoMapper,
            locationEntityMapper,
            locationMapper,
            locationApi,
            appDb
        )
}
