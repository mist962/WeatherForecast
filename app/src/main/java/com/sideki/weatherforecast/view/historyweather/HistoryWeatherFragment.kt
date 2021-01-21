package com.sideki.weatherforecast.view.historyweather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sideki.weatherforecast.R
import com.sideki.weatherforecast.adapters.HistoryWeatherAdapter
import com.sideki.weatherforecast.databinding.FragmentHistoryWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_history_weather.*

@AndroidEntryPoint
class HistoryWeatherFragment : Fragment(R.layout.fragment_history_weather) {

    private var _binding: FragmentHistoryWeatherBinding? = null
    private val binding get() = _binding

    private val historyWeatherViewModel by viewModels<HistoryWeatherViewModel>()

    private val adapter by lazy { HistoryWeatherAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerview.adapter = adapter
            recyclerview.setHasFixedSize(true)
        }
        historyWeatherViewModel.historyWeather.observe(viewLifecycleOwner, {
            adapter.getWeather(it)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}