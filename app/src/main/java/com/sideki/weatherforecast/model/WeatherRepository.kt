package com.sideki.weatherforecast.model

import androidx.lifecycle.LiveData
import com.sideki.weatherforecast.api.WeatherApi
import com.sideki.weatherforecast.model.data.WeatherDao
import com.sideki.weatherforecast.model.entities.WeatherDB
import com.sideki.weatherforecast.model.entities.WeatherResponse
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    val weatherApi: WeatherApi,
    val weatherDao: WeatherDao
) {

    fun getCurrentWeather(
        query: String,
        units: String,
        lang: String,
        key: String
    ): Call<WeatherResponse> {
        return weatherApi.getCurrentWeatherApi(query, units, lang, key)
    }

    val readAllWeather: LiveData<List<WeatherDB>> = weatherDao.getAllWeather()

    suspend fun addHistoryWeather(weatherDB: WeatherDB) {
        weatherDao.addWeather(weatherDB)
    }

}