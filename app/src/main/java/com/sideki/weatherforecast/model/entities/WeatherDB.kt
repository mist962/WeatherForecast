package com.sideki.weatherforecast.model.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "weather_table", primaryKeys = arrayOf("id", "cityName") )
data class WeatherDB(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "cityName")
    val cityName: String,
    val temperature: Int,
    val image: Bitmap?
) : Parcelable {
}