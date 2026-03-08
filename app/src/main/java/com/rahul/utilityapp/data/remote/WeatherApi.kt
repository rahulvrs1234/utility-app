package com.rahul.utilityapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.rahul.utilityapp.data.model.WeatherResponse

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true
    ): WeatherResponse
}