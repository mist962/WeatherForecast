package com.sideki.weatherforecast.di

import android.content.Context
import com.sideki.weatherforecast.api.NetworcConnectionInterceptor
import com.sideki.weatherforecast.api.WeatherApi
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
    fun provieOkHttp(
        @ApplicationContext context: Context
    ): OkHttpClient=
        OkHttpClient.Builder().addInterceptor(NetworcConnectionInterceptor(context)).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context
    ): Retrofit =
        Retrofit.Builder()
            .client(provieOkHttp(context))
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)
}