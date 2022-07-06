package com.example.weatherapp.data.dataCurrent

data class CurrentWeather(
    val base: String? = null,
    val clouds: Clouds,
    val cod: Int? = null,
    val coord: Coord? = null,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain? = null,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)