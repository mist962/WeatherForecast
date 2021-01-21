package com.sideki.weatherforecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sideki.weatherforecast.R
import com.sideki.weatherforecast.model.entities.WeatherDB
import kotlinx.android.synthetic.main.item_weather.view.*

class HistoryWeatherAdapter :
    RecyclerView.Adapter<HistoryWeatherAdapter.HistoryWeatherViewHolder>() {

    private var weatherList = emptyList<WeatherDB>()

    inner class HistoryWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryWeatherViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
    )

    override fun onBindViewHolder(holder: HistoryWeatherViewHolder, position: Int) {
        val curItem = weatherList[position]

        holder.itemView.text_item_city_name.text = curItem.cityName
        holder.itemView.text_item_temperature.text = "${curItem.temperature}°"
        holder.itemView.image_item.setImageBitmap(curItem.image)
    }

    override fun getItemCount() = weatherList.size

    fun getWeather(_weatherList: List<WeatherDB>) {
        weatherList = _weatherList
        notifyDataSetChanged()
    }
}