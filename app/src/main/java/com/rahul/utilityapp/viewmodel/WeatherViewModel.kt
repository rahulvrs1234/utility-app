package com.rahul.utilityapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.utilityapp.data.model.WeatherResponse
import com.rahul.utilityapp.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    var weather = mutableStateOf<WeatherResponse?>(null)
        private set

    var isLoading = mutableStateOf(false)
        private set

    init {
        fetchWeather()
    }

    private fun fetchWeather() {

        viewModelScope.launch {

            try {
                isLoading.value = true

                weather.value =
                    repository.getWeather(
                        latitude = 1.3521,
                        longitude = 103.8198
                    )

            } catch (e: Exception) {
                e.printStackTrace()
            }

            isLoading.value = false
        }
    }
}