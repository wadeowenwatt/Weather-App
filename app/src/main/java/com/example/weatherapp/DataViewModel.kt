package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherData
import com.example.weatherapp.network.WeatherApi
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DataViewModel() : ViewModel() {

    //https://api.openweathermap.org/data/2.5/weather?lat=21.030653&lon=105.847130&appid=2f1f308ae7e8589c60232d6f42aa9631
    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData : LiveData<WeatherData> = _weatherData

    private val _status = MutableLiveData<String>()
    val status : LiveData<String> = _status

    private val lat = "21.0245"
    private val lon = "105.8412"
    private val keyID = "2f1f308ae7e8589c60232d6f42aa9631"

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

        when (currentDateAndTime.day) {
            1 -> return "Monday"
            2 -> return "Tuesday"
            3 -> return "Wednesday"
            4 -> return "Thursday"
            5 -> return "Friday"
            6 -> return "Saturday"
            else -> return "Sunday"
        }
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            try {
                _weatherData.value = WeatherApi.retrofitService.getCurrentWeather(lat, lon, keyID)
                Log.e("callback", "call API")
            } catch (e : Exception) {
                _status.value = e.toString()
            }
        }
    }
}