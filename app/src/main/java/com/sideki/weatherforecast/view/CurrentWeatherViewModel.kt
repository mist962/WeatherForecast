package com.sideki.weatherforecast.view

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sideki.weatherforecast.model.WeatherRepository
import com.sideki.weatherforecast.model.entities.WeatherResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CurrentWeatherViewModel @ViewModelInject constructor(val weatherRepository: WeatherRepository) :
    ViewModel() {

    val currentWeather: MutableLiveData<Response<WeatherResponse>> = MutableLiveData()

    fun getCurrentWeather(query: String, units: String, lang: String, key: String) {
        viewModelScope.launch {
            currentWeather.value = weatherRepository.getCurrentWeather(query, units, lang, key)
        }
    }

}