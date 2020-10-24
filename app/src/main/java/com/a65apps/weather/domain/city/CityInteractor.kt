package com.a65apps.weather.domain.city

import kotlinx.coroutines.flow.Flow

interface CityInteractor {
    suspend fun filter(name: String)
    suspend fun searchCities(name: String)
    fun subsribeCities(): Flow<List<City>>
    suspend fun citySelected(city: City)
    suspend fun getDefault(): City?
    fun setDefault(city: City?)
}
