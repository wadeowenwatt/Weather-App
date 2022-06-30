package com.example.weatherapp.network

import com.example.weatherapp.dataCurrent.CurrentWeather
import com.example.weatherapp.dataOneCall.WeatherData
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

object SearchApi {
    val retrofitService : SearchApiService by lazy {
        retrofit.create(SearchApiService::class.java)
    }
}

interface SearchApiService {
    @GET("data/2.5/weather?")
    suspend fun getSearchWeather(@Query("q") q: String,
                                  @Query("appid") appid: String) : CurrentWeather
}