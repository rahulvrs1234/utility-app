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

    var errorMessage = mutableStateOf<String?>(null)
        private set

    init {
        refreshWeather()
    }

    fun refreshWeather() {
        if (isLoading.value) return

        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                weather.value =
                    repository.getWeather(
                        latitude = 1.3521,
                        longitude = 103.8198
                    )
            } catch (_: Exception) {
                errorMessage.value = "Unable to refresh weather right now."
            }

            isLoading.value = false
        }
    }
}
