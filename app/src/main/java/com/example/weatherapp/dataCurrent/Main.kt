package com.example.weatherapp.dataCurrent

data class Main(
    val feels_like: Double? = null,
    val grnd_level: Int? = null,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int? = null,
    val temp: Double,
    val temp_max: Double? = null,
    val temp_min: Double? = null
)