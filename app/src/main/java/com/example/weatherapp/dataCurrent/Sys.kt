package com.example.weatherapp.dataCurrent

data class Sys(
    val country: String? = null,
    val id: Int? = null,
    val sunrise: Int,
    val sunset: Int,
    val type: Int? = null
)