package com.sideki.weatherforecast.model.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    var weather: List<Weather>, //Нужно
    var main: Main, //температура
    var visibility: Int, //видимость
    var wind: Wind, //ветер содержит угол и скорость
    var clouds: Clouds, //Процент облачности
    var dt: Int, //дата в UTC
    var sys: Sys, // время рассвета и заката
    var name: String, // нейм города
) {

    data class Weather(
        var main: String,
        var icon: String
    )

    data class Main(
        var temp: Double,
        @SerializedName("feels_like")
        var feelsLike: Double,
        @SerializedName("temp_min")
        var tempMin: Double,
        @SerializedName("temp_max")
        var tempMax: Double,
        var humidity: Int //Влажность
    )

    data class Wind(
        var speed: Double,
        var deg: Int
    )

    data class Clouds(
        var all: Int // облачность в %
    )

    data class Sys(
        var sunrise: Int,
        var sunset: Int
    )

}