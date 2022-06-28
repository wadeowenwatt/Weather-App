package com.example.weatherapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class PredictHourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val hourText : TextView = itemView.findViewById(R.id.hours)
    val iconStatus : ImageView = itemView.findViewById(R.id.status_icon_per_hour)
    val temp : TextView = itemView.findViewById(R.id.temperature)
    val rainPercent : TextView = itemView.findViewById(R.id.humidity_percent)
}
