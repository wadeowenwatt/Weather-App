package com.example.weatherapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class LocationManageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cityName : TextView = itemView.findViewById(R.id.city_name_in_location_manage)
    val temp : TextView = itemView.findViewById(R.id.temp_in_location_manage)
    val iconWeather : ImageView = itemView.findViewById(R.id.icon_weather_in_location_manage)
    val status : TextView = itemView.findViewById(R.id.weather_status_in_location_manage)
}
