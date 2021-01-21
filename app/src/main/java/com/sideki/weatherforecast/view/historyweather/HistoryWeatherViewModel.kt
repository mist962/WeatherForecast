package com.sideki.weatherforecast.view.historyweather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sideki.weatherforecast.model.WeatherRepository
import com.sideki.weatherforecast.model.entities.WeatherDB

class HistoryWeatherViewModel @ViewModelInject constructor(
    val weatherRepository: WeatherRepository,
) : ViewModel() {

    var historyWeather: LiveData<List<WeatherDB>> = weatherRepository.readAllWeather
}