package com.rahul.utilityapp.data.repository

import com.rahul.utilityapp.data.remote.RetrofitInstance

class WeatherRepository {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ) =
        RetrofitInstance.api.getWeather(
            latitude,
            longitude
        )
}