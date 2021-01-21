package com.sideki.weatherforecast.di

import android.content.Context
import com.sideki.weatherforecast.api.NetworkConnectionInterceptor
import com.sideki.weatherforecast.api.WeatherApi
import com.sideki.weatherforecast.model.data.DataBase
import com.sideki.weatherforecast.model.data.WeatherDao
import com.sideki.weatherforecast.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(
        @ApplicationContext context: Context
    ): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(NetworkConnectionInterceptor(context)).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context
    ): Retrofit =
        Retrofit.Builder()
            .client(provideOkHttp(context))
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)


    @Provides
    fun provideWeatherDao(@ApplicationContext context: Context): WeatherDao =
        DataBase.getDataBase(context).weatherDao()
}