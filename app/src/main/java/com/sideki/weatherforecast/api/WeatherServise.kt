package com.sideki.weatherforecast.api

import com.sideki.weatherforecast.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherServise {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}