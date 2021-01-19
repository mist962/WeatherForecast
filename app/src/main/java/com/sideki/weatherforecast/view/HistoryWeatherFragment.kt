package com.sideki.weatherforecast.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sideki.weatherforecast.R
import com.sideki.weatherforecast.databinding.FragmentHistoryWeatherBinding

class HistoryWeatherFragment : Fragment(R.layout.fragment_history_weather) {

    private var _binding: FragmentHistoryWeatherBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "History"

        binding.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}