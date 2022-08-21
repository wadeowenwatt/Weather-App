package com.example.weatherapp.network

import com.example.weatherapp.data.dataOneCall.WeatherData
import com.example.weatherapp.config.FileConfig.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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

interface WeatherApiService {
    @GET("data/2.5/onecall?")
    suspend fun getOneCallWeather(@Query("lat") lat: String,
                                  @Query("lon") lon: String,
                                  @Query("appid") appid: String) : WeatherData
}