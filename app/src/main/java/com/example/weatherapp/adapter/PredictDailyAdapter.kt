package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.viewmodel.DataViewModel
import com.example.weatherapp.R
import com.example.weatherapp.data.dataOneCall.Daily

class PredictDailyAdapter(
    private val listDaily : List<Daily>,
    private val viewModel : DataViewModel
) :
    RecyclerView.Adapter<PredictDailyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictDailyViewHolder {
        val layoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_weather_per_daily, parent, false)
        return PredictDailyViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: PredictDailyViewHolder, position: Int) {
        val element = listDaily[position]

        holder.dayInWeek.text = viewModel.convertEpochToDayOfWeek(element.dt)

        val iconUrl = "http://openweathermap.org/img/wn/" + element.weather[0].icon + "@2x.png"
        val uri = iconUrl.toUri().buildUpon().scheme("https").build()
        holder.statusWeather.load(uri)

        if (viewModel.typeDegree == "C") {
            holder.temp.text = "${(element.temp.day - 273.15).toInt()}°"
        } else {
            holder.temp.text = "${((element.temp.day - 273.15) * (9 / 5) + 32).toInt()}°"
        }
    }

    override fun getItemCount() = 7
}