package com.sideki.weatherforecast.utils

interface ViewStates {

    fun noInternetView() {}

    fun isSuccessfulView() {}

    fun cityNotFoundView() {}

    fun allGoneView() {}

    fun showProgressView() {}

}