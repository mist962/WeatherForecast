package com.sideki.weatherforecast.model

import com.sideki.weatherforecast.api.WeatherServise
import com.sideki.weatherforecast.model.entities.WeatherResponse
import retrofit2.Response

class WeatherRepository {

    suspend fun getCurrentWeather(query: String, units: String, lang:String, key: String): Response<WeatherResponse>{
        return WeatherServise.weatherApi.getCurrentWeatherApi(query, units, lang, key)
    }

    suspend fun getHistoryWeather(){

    }

}