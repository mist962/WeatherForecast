package com.sideki.weatherforecast.model.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "weather_table")
data class WeatherDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cityName: String,
    val temperature: Int,
    val image: Bitmap?
) : Parcelable {
}