package com.a65apps.weather.data.core.network

import com.a65apps.weather.data.city.CityDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_LANG = "ru"

interface CityApi {
    @GET("search.php?format=json")
    fun search(
        @Query("q") cityName: String,
        @Query("accept-language") language: String = DEFAULT_LANG
    ): Call<List<CityDto>>
}
