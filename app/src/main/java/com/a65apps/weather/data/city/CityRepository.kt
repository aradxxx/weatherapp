package com.a65apps.weather.data.city

import com.a65apps.weather.domain.city.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun updateCities(name: String)
    fun subscribeCities(): Flow<List<City>>
    suspend fun updateCity(city: City)
    suspend fun savedCities(): List<City>
}
