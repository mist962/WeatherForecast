package com.sideki.weatherforecast.model.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    var weather: List<Weather>,
    var timezone: String,
    var main: Main, //Температура
    var visibility: Int, //Видимость
    var wind: Wind, //Направление и скорость ветра
    var clouds: Clouds, //Процент облачности
    var dt: Int, //Дата в UTC
    var sys: Sys, //Время рассвета и заката
    var name: String, // Нейм города
) {

    data class Weather(
        var main: String,
        val description: String,
        var icon: String
    )

    data class Main(
        var temp: Double,
        @SerializedName("feels_like")
        var feelsLike: Double,
        var humidity: Int //Влажность
    )

    data class Wind(
        var speed: Double,
        var deg: Int
    )

    data class Clouds(
        var all: Int // Облачность в %
    )

    data class Sys(
        var sunrise: Int,
        var sunset: Int
    )

}