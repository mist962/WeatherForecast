package com.sideki.weatherforecast.model

import com.sideki.weatherforecast.api.WeatherApi
import com.sideki.weatherforecast.model.entities.WeatherResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(
        query: String,
        units: String,
        lang: String,
        key: String
    ): Response<WeatherResponse> {
        return weatherApi.getCurrentWeatherApi(query, units, lang, key)
    }

    suspend fun getHistoryWeather() {

    }

}