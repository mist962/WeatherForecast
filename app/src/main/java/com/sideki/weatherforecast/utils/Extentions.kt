package com.sideki.weatherforecast.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.isOnline(): Boolean {
    val connectivityManager = this
        .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.apply {
        val netInfo = activeNetworkInfo
        netInfo?.let {
            if (it.isConnected) return true
        }
    }
    return false
}