package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weatherapp.R
import com.example.weatherapp.dataCurrent.CurrentWeather

class LocationManageAdapter(private val listCity : MutableList<CurrentWeather>) : RecyclerView.Adapter<LocationManageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationManageViewHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.weather_location, parent, false)
        return LocationManageViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: LocationManageViewHolder, position: Int) {
        val element = listCity[position]
        holder.cityName.text = element.name
        holder.temp.text = "${(element.main.temp - 273.15).toInt()}°"

        val iconUrl = "http://openweathermap.org/img/wn/" + element.weather[0].icon + "@2x.png"
        val uri = iconUrl.toUri().buildUpon().scheme("https").build()
        holder.iconWeather.load(uri)

        holder.status.text = element.weather[0].main
    }

    override fun getItemCount(): Int = listCity.size

}