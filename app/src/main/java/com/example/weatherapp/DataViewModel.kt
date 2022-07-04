package com.example.weatherapp

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.dataCurrent.CurrentWeather
import com.example.weatherapp.dataOneCall.WeatherData
import com.example.weatherapp.network.CurrentApi
import com.example.weatherapp.network.SearchApi
import com.example.weatherapp.network.WeatherApi
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DataViewModel() : ViewModel() {

    //https://api.openweathermap.org/data/2.5/weather?lat=21.030653&lon=105.847130&appid=2f1f308ae7e8589c60232d6f42aa9631
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData : LiveData<WeatherData> = _weatherData

    private val _currentData = MutableLiveData<CurrentWeather>()
    val currentData : LiveData<CurrentWeather> = _currentData

    private val _searchData = MutableLiveData<CurrentWeather>()
    val searchData : LiveData<CurrentWeather> = _searchData

    private var _listSearchData = MutableLiveData<MutableList<CurrentWeather>>(mutableListOf())
    val listSearchData : LiveData<MutableList<CurrentWeather>> = _listSearchData

    var typeDegree = "C"

    var typeWind = "km/h"

    var typeAtmos = "mbar"

    private var count = 0

    private val _status = MutableLiveData<String>()
    val status : LiveData<String> = _status

    private val lat = "21.0245"
    private val lon = "105.8412"
    private val keyID = "2f1f308ae7e8589c60232d6f42aa9631"

    var q = "Hanoi"


    init {
        getWeatherData()
    }

    fun convertEpochToHour(s: Int): String? {
        val currentDateAndTime = Date(s.toLong() * 1000)
        return SimpleDateFormat("kk:mm").format(currentDateAndTime)
    }

    fun convertEpochToDay(s: Int): String? {
        val sumEpoch = s + 25200 // GMT+7
        val currentDateAndTime = Date(sumEpoch.toLong() * 1000)

        return SimpleDateFormat("MMM dd").format(currentDateAndTime)
    }

    fun convertEpochToDayOfWeek(s: Int): String? {
        val sumEpoch = s + 25200 // GMT+7
        val currentDateAndTime = Date(sumEpoch.toLong() * 1000)

        return when (currentDateAndTime.day) {
            1 -> "Mon"
            2 -> "Tues"
            3 -> "Wed"
            4 -> "Thurs"
            5 -> "Fri"
            6 -> "Sat"
            else -> "Sun"
        }
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            try {
                _weatherData.value = WeatherApi.retrofitService.getOneCallWeather(lat, lon, keyID)
                _currentData.value = CurrentApi.retrofitService.getCurrentWeather(lat, lon, keyID)
                _searchData.value = SearchApi.retrofitService.getSearchWeather(currentData.value!!.name, keyID)
                if (count == 0) {
                    listSearchData.value?.add(searchData.value!!)
                    count++
                }
                Log.e("callback", "call API")
            } catch (e : Exception) {
//                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getSearchData() {
        viewModelScope.launch {
            try {
                _searchData.value = SearchApi.retrofitService.getSearchWeather(q, keyID)
                listSearchData.value?.add(_searchData.value!!)
            } catch(e: Exception) {
//                _status.value = e.toString()
            }
        }
    }
}