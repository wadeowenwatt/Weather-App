package com.example.weatherapp.data

import com.example.weatherapp.data.dataCurrent.CurrentWeather
import com.example.weatherapp.data.dataOneCall.WeatherData
import com.example.weatherapp.domain.IRepo
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val api: WeatherApi) : IRepo {
    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        appId: String
    ): CurrentWeather {
        return api.getCurrentWeather(lat, lon, appId)
    }

    override suspend fun getSearchWeather(
        q: String,
        appId: String
    ): CurrentWeather {
        return api.getSearchWeather(q, appId)
    }

    override suspend fun getOneCallWeather(
        lat: String,
        lon: String,
        appId: String
    ): WeatherData {
        return api.getOneCallWeather(lat, lon, appId)
    }
}