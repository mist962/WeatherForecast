package com.sideki.weatherforecast.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sideki.weatherforecast.model.WeatherRepository

class CurrentWeatherViewModelFactory(private val weatheRepository: WeatherRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(weatheRepository) as T
    }
}