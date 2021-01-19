package com.sideki.weatherforecast.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.sideki.weatherforecast.R
import com.sideki.weatherforecast.databinding.FragmentCurrentWeatherBinding
import com.sideki.weatherforecast.model.WeatherRepository
import com.sideki.weatherforecast.utils.API_KEY
import kotlinx.android.synthetic.main.fragment_current_weather.*

class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    private val weatherRepository = WeatherRepository()
    private var currentWeatherViewModel = CurrentWeatherViewModel(weatherRepository)
    private val currentWeatherViewModelFactory = CurrentWeatherViewModelFactory(weatherRepository)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Current Weather"
        _binding = FragmentCurrentWeatherBinding.bind(view)

        currentWeatherViewModel = ViewModelProvider(
            this,
            currentWeatherViewModelFactory
        ).get(CurrentWeatherViewModel::class.java)

        currentWeatherViewModel.getCurrentWeather("Зимбабве", "metric", "ru", API_KEY)
        currentWeatherViewModel.currentWeather.observe(viewLifecycleOwner, { weatherResponse ->
            if (weatherResponse.isSuccessful) {
                binding.apply {
                    text_city.text = weatherResponse.body()?.name

                    weatherResponse.body()?.weather?.forEach {
                        text_weather.text = it.main
                        Glide.with(this@CurrentWeatherFragment)
                            .load("http://openweathermap.org/img/wn/${it.icon}@4x.png").into(imageWeather)
                    }
                    val temperature = (weatherResponse.body()?.main?.temp)?.toInt()
                    text_temperature.text = "${temperature.toString()}°"
                    text_temperature_filings.text =
                        weatherResponse.body()?.main?.feelsLike.toString()
                    text_temperature_min.text = weatherResponse.body()?.main?.tempMin.toString()
                    text_temperature_max.text = weatherResponse.body()?.main?.tempMax.toString()
                    /*text_date.text = weatherResponse
                    text_cloudy.text = weatherResponse
                    text_vlaga.text = weatherResponse
                    text_vidimost.text = weatherResponse
                    text_wind_speed.text = weatherResponse
                    text_wint_deg.text = weatherResponse*/
                    //рассвет
                    //закат
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}