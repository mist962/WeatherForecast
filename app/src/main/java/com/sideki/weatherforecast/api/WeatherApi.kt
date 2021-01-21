package com.sideki.weatherforecast.api

import com.sideki.weatherforecast.model.entities.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getCurrentWeatherApi(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") key: String,
    ): Call<WeatherResponse>
}