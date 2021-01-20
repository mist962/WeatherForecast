package com.sideki.weatherforecast.utils

class AppQueryTextListener(val onSuccess: (String) -> Unit) :
    android.widget.SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String): Boolean {
        onSuccess(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}