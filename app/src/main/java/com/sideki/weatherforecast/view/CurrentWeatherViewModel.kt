package com.sideki.weatherforecast.view

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sideki.weatherforecast.model.WeatherRepository
import com.sideki.weatherforecast.model.entities.WeatherDB
import com.sideki.weatherforecast.model.entities.WeatherResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CurrentWeatherViewModel @ViewModelInject constructor(
    val weatherRepository: WeatherRepository,
    application: Application
) :
    AndroidViewModel(application) {

    val currentWeather: MutableLiveData<Call<WeatherResponse>> = MutableLiveData()

    fun getCurrentWeather(query: String, units: String, lang: String, key: String) {
        viewModelScope.launch {
            currentWeather.value = weatherRepository.getCurrentWeather(query, units, lang, key)
        }
    }

    fun addWeather(weatherDB: WeatherDB) {
        viewModelScope.launch {
            weatherRepository.addHistoryWeather(weatherDB)
        }
    }

}