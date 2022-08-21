package com.example.weatherapp.data

import com.example.weatherapp.data.dataCurrent.CurrentWeather
import com.example.weatherapp.data.dataOneCall.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): CurrentWeather

    @GET("data/2.5/weather?")
    suspend fun getSearchWeather(
        @Query("q") q: String,
        @Query("appid") appid: String
    ): CurrentWeather

    @GET("data/2.5/onecall?")
    suspend fun getOneCallWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): WeatherData
}