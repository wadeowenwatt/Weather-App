package com.example.weatherapp.data.dataOneCall

data class WeatherData(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>? = null,
    val timezone: String,
    val timezone_offset: Int
)