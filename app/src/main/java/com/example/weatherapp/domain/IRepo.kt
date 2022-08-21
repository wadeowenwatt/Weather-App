package com.example.weatherapp.domain

import com.example.weatherapp.data.dataCurrent.CurrentWeather
import com.example.weatherapp.data.dataOneCall.WeatherData

interface IRepo {
    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        appId: String
    ): CurrentWeather

    suspend fun getSearchWeather(
        q: String,
        appId: String
    ): CurrentWeather

    suspend fun getOneCallWeather(
        lat: String,
        lon: String,
        appId: String
    ): WeatherData
}