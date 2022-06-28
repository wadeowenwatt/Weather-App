package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.DataViewModel
import com.example.weatherapp.R
import com.example.weatherapp.data.Hourly
import java.util.*

class PredictHourlyAdapter(private val listHourlyPredict : List<Hourly>) :
    RecyclerView.Adapter<PredictHourlyViewHolder>() {

    private val viewModel = DataViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictHourlyViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.weather_per_hour, parent, false)
        return PredictHourlyViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: PredictHourlyViewHolder, position: Int) {
        val element = listHourlyPredict[position]

        if (Date().hours == Date(element.dt.toLong() * 1000).hours) {
            holder.hourText.text = "Now"
        } else {
            holder.hourText.text = viewModel.convertEpochToHour(element.dt)
        }

        val iconUrl = "http://openweathermap.org/img/wn/" + element.weather[0].icon + "@2x.png"
        val uri = iconUrl.toUri().buildUpon().scheme("https").build()
        holder.iconStatus.load(uri)

        holder.temp.text = "${(element.temp - 273.15).toInt()}\u2103"

        holder.rainPercent.text = element.humidity.toString() + "% humidity"
    }

    override fun getItemCount(): Int = 24
}