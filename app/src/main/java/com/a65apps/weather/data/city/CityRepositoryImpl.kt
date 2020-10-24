package com.a65apps.weather.data.city

import com.a65apps.weather.data.core.AppDb
import com.a65apps.weather.data.core.network.CityApi
import com.a65apps.weather.di.core.Mapper
import com.a65apps.weather.domain.city.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val cityDtoMapper: Mapper<CityDto, CityEntity>,
    private val cityEntityMapper: Mapper<CityEntity, City>,
    private val cityMapper: Mapper<City, CityEntity>,
    private val cityApi: CityApi,
    private val appDb: AppDb
) : CityRepository {
    override suspend fun updateCities(name: String) = withContext(Dispatchers.IO) {
        val response = cityApi.search(name).execute()
        val cities = if (!response.isSuccessful) {
            throw IOException(response.message())
        } else {
            response.body()
        } ?: emptyList()
        val cityEntities = cityDtoMapper.map(cities)
        appDb.cityDao().insertCities(cityEntities)
        return@withContext
    }

    override fun subscribeCities(): Flow<List<City>> {
        return appDb.cityDao().subscribeCities().map { cityEntityMapper.map(it) }
    }

    override suspend fun updateCity(city: City) = withContext(Dispatchers.IO) {
        val cityEntity = cityMapper.map(city)
        appDb.cityDao().insertCity(cityEntity)
    }

    override suspend fun savedCities(): List<City> {
        return appDb.cityDao().getSavedCities().map { cityEntityMapper.map(it) }
    }
}
