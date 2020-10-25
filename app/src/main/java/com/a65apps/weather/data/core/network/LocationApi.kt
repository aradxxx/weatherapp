package com.a65apps.weather.data.core.network

import com.a65apps.weather.data.location.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_LANG = "ru"

interface LocationApi {
    @GET("search.php?format=json")
    suspend fun search(
        @Query("city") query: String,
        @Query("accept-language") language: String = DEFAULT_LANG
    ): List<LocationDto>
}
