package com.example.weatherapp.network

import com.example.weatherapp.data.WeatherData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.openweathermap.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

//https://api.openweathermap.org/data/2.5/weather?lat=21.030653&lon=105.847130&appid=2f1f308ae7e8589c60232d6f42aa9631
interface WeatherApiService {
    @GET("data/2.5/onecall?")
    suspend fun getCurrentWeather(@Query("lat") lat: String,
                                  @Query("lon") lon: String,
                                  @Query("appid") appid: String) : WeatherData
}