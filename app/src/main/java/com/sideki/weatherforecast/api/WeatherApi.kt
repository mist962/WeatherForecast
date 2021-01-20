package com.sideki.weatherforecast.api

import com.sideki.weatherforecast.model.entities.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    //http://api.openweathermap.org/data/2.5/weather?q=London&appid=141bec87e346370bff8be5569c469afa
    @GET("weather")
    fun getCurrentWeatherApi(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") key: String,
    ): Call<WeatherResponse>
}