package com.sideki.weatherforecast.model.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sideki.weatherforecast.model.entities.WeatherDB

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWeather(weather: WeatherDB)

    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): LiveData<List<WeatherDB>>

}