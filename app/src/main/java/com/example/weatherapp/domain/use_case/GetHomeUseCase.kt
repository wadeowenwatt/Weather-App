package com.example.weatherapp.domain.use_case

import com.example.weatherapp.config.FileConfig.KEY_ID
import com.example.weatherapp.config.Resource
import com.example.weatherapp.data.dataOneCall.WeatherData
import com.example.weatherapp.domain.IRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val repo: IRepo
) {
    var lat = "21.0245"
    var lon = "105.8412"
    operator fun invoke(): Flow<Resource<List<WeatherData>>> = flow {
        try {
            emit(Resource.Loading())

            val weatherData = listOf(repo.getOneCallWeather(lat, lon, KEY_ID))

            emit(Resource.Success(weatherData))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred. "))
        } catch (e : IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server. Check your internet connection."))
        }

    }
}