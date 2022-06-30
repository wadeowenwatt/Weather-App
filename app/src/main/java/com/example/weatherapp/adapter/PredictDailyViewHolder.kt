package com.example.weatherapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class PredictDailyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val dayInWeek : TextView = itemView.findViewById(R.id.day_week)
    val statusWeather : ImageView = itemView.findViewById(R.id.status_icon_per_daily)
    val temp : TextView = itemView.findViewById(R.id.temperature_per_daily)
}
