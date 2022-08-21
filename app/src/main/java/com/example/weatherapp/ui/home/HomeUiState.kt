package com.example.weatherapp.ui.home

import com.example.weatherapp.data.dataOneCall.WeatherData

data class HomeUiState(
    val isLoading: Boolean = false,
    var home: List<WeatherData> = emptyList(),
    val error: String = ""
)
