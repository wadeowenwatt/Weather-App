package com.example.weatherapp.domain

import com.example.weatherapp.data.dataCurrent.CurrentWeather
import com.example.weatherapp.domain.model.HomeCityName

fun CurrentWeather.toHomeCityName(): HomeCityName {
    return HomeCityName(
        cityName = name
    )
}

