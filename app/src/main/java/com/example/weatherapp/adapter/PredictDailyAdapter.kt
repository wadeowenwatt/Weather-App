package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.DataViewModel
import com.example.weatherapp.R
import com.example.weatherapp.dataOneCall.Daily

class PredictDailyAdapter(private val listDaily : List<Daily>) :
    RecyclerView.Adapter<PredictDailyViewHolder>() {

    private val viewModel = DataViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictDailyViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_per_daily, parent, false)
        return PredictDailyViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: PredictDailyViewHolder, position: Int) {
        val element = listDaily[position]

        holder.dayInWeek.text = viewModel.convertEpochToDayOfWeek(element.dt)

        val iconUrl = "http://openweathermap.org/img/wn/" + element.weather[0].icon + "@2x.png"
        val uri = iconUrl.toUri().buildUpon().scheme("https").build()
        holder.statusWeather.load(uri)

        holder.temp.text = "${(element.temp.day - 273.15).toInt()}°"
    }

    override fun getItemCount() = 7
}