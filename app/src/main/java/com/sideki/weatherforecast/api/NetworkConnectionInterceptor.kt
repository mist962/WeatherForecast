package com.sideki.weatherforecast.api

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.sideki.weatherforecast.utils.NoInternetException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    @ApplicationContext context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvaible()) throw NoInternetException("Не удалось установить соединение")
        return chain.proceed(chain.request())
    }

    fun isInternetAvaible(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}