package com.example.weathercompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercompose.api.Repository
import com.example.weathercompose.data.WeatherModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel(val repository: Repository) : ViewModel() {
    var weatherData by mutableStateOf<WeatherModel?>(null)
        private set

    fun getData(city: String, days: Int) {
        viewModelScope.launch {
            val weatherResponse = repository.getData(city, days)
            if (weatherResponse != null) {
                weatherData = weatherResponse
            }

        }

    }

}