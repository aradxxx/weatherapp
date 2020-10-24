package com.a65apps.weather.di.citysearch

import com.a65apps.weather.data.city.CityDto
import com.a65apps.weather.data.city.CityDtoMapper
import com.a65apps.weather.data.city.CityEntity
import com.a65apps.weather.data.city.CityEntityMapper
import com.a65apps.weather.data.city.CityMapper
import com.a65apps.weather.data.city.CityRepository
import com.a65apps.weather.data.city.CityRepositoryImpl
import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.network.CityApi
import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.city.City
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CitySearchModule {
    @Provides
    @Singleton
    fun provideCityEntityMapper(): Mapper<CityEntity, City> = CityEntityMapper

    @Provides
    @Singleton
    fun provideCityDtoMapper(): Mapper<CityDto, CityEntity> = CityDtoMapper

    @Provides
    @Singleton
    fun provideCityMapper(): Mapper<City, CityEntity> = CityMapper

    @Provides
    @Singleton
    fun provideCityRepository(
        cityDtoMapper: Mapper<CityDto, CityEntity>,
        cityEntityMapper: Mapper<CityEntity, City>,
        cityMapper: Mapper<City, CityEntity>,
        cityApi: CityApi,
        appDb: AppDb
    ): CityRepository =
        CityRepositoryImpl(
            cityDtoMapper,
            cityEntityMapper,
            cityMapper,
            cityApi,
            appDb
        )
}
