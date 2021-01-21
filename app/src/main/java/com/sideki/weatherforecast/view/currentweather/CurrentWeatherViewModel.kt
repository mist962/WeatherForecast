package com.sideki.weatherforecast.view.currentweather

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sideki.weatherforecast.model.WeatherRepository
import com.sideki.weatherforecast.model.entities.WeatherDB
import com.sideki.weatherforecast.model.entities.WeatherResponse
import kotlinx.coroutines.launch
import retrofit2.Call

class CurrentWeatherViewModel @ViewModelInject constructor(
    val weatherRepository: WeatherRepository,
    application: Application,
) :
    AndroidViewModel(application) {

    val currentWeather: MutableLiveData<Call<WeatherResponse>> = MutableLiveData()

    fun getCurrentWeather(query: String, units: String, lang: String, key: String) {
        viewModelScope.launch {
            currentWeather.value = weatherRepository.getCurrentWeather(query, units, lang, key)
        }
    }

    //Добавляем в локаль
    fun addWeather(weatherDB: WeatherDB) {
        viewModelScope.launch {
            weatherRepository.addHistoryWeather(weatherDB)
        }
    }

}