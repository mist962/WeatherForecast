package com.sideki.weatherforecast.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sideki.weatherforecast.R
import com.sideki.weatherforecast.databinding.FragmentCurrentWeatherBinding
import com.sideki.weatherforecast.model.WeatherRepository
import com.sideki.weatherforecast.model.entities.WeatherResponse
import com.sideki.weatherforecast.utils.API_KEY
import com.sideki.weatherforecast.utils.NoInternetException
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather) {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    private val currentWeatherViewModel by viewModels<CurrentWeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrentWeatherBinding.bind(view)

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentWeatherViewModel.getCurrentWeather(
                    query.toString(),
                    "metric",
                    "ru",
                    API_KEY
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        try {
            currentWeatherViewModel.getCurrentWeather("Москва", "metric", "ru", API_KEY)
            currentWeatherViewModel.currentWeather.observe(viewLifecycleOwner, { weatherResponse ->
                if (weatherResponse.isSuccessful) {
                    binding.apply {
                        progress_bar.visibility = View.GONE
                        text_city_not_found.visibility = View.GONE
                        constraintMain.visibility = View.VISIBLE

                        //Название города
                        text_city.text = weatherResponse.body()?.name

                        //Иконка погоды и наименование
                        weatherResponse.body()?.weather?.forEach {

                            text_description.text = (it.description).capitalize(Locale.ROOT)

                            GlobalScope.launch(Dispatchers.IO) {
                                val drawblwe_for_text = Glide.with(this@CurrentWeatherFragment)
                                    .asDrawable()
                                    .load("http://openweathermap.org/img/wn/${it.icon}@4x.png")
                                    .submit(100, 100)
                                    .get()
                                withContext(Dispatchers.Main) {
                                    text_description.setCompoundDrawablesWithIntrinsicBounds(
                                        null,
                                        drawblwe_for_text,
                                        null,
                                        null
                                    )
                                }
                            }
                        }

                        //Температура
                        val temperature = (weatherResponse.body()?.main?.temp)?.toInt()
                        text_temperature.text = "${temperature.toString()}°"
                        val temperature_filings = (weatherResponse.body()?.main?.feelsLike)?.toInt()
                        text_temperature_filings.text =
                            "Ощущается как: ${temperature_filings.toString()}°"

                        //Текущее время города
                        val unixSeconds = (weatherResponse.body()?.dt)!!.toLong()
                        val date = Date(unixSeconds * 1000L)
                        val sdf = SimpleDateFormat("HH:mm | dd-MM-yy | z")
                        val timeZone = ((weatherResponse.body()!!.timezone).toInt()) / 3600
                        when {
                            timeZone > 0 -> {
                                sdf.timeZone = TimeZone.getTimeZone("GMT+$timeZone")
                            }
                            timeZone < 0 -> {
                                sdf.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                            }
                            else -> {
                                sdf.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                            }
                        }
                        val formattedDate: String = sdf.format(date)
                        text_date.text = formattedDate

                        text_cloudy.text =
                            "Облачность: ${(weatherResponse.body()?.clouds?.all).toString()}%"
                        text_vlaga.text =
                            "Влажность: ${(weatherResponse.body()?.main?.humidity).toString()}%"
                        text_vidimost.text =
                            "Видимость: ${((weatherResponse.body()?.visibility)?.div(1000)).toString()}км"
                        text_wind_speed.text =
                            "Скорость ветра: ${(weatherResponse.body()?.wind?.speed).toString()}м/c"

                        val wind_deg = weatherResponse.body()?.wind?.deg!!
                        if (wind_deg in 0..90) {
                            text_wint_deg.text =
                                "Напр-е ветра: Восточное"
                        } else if (wind_deg in 91..180) {
                            text_wint_deg.text =
                                "Напр-е ветра: Северное"
                        } else if (wind_deg in 181..270) {
                            text_wint_deg.text =
                                "Напр-е ветра: Западное"
                        } else {
                            text_wint_deg.text =
                                "Напр-е ветра: Южное"
                        }

                        //Рассвет
                        val unixSeconds_sunrise = (weatherResponse.body()?.sys?.sunrise)!!.toLong()
                        val date_sunrise = Date(unixSeconds_sunrise * 1000L)
                        val sdf_sunrise = SimpleDateFormat("HH:mm")
                        when {
                            timeZone > 0 -> {
                                sdf_sunrise.timeZone = TimeZone.getTimeZone("GMT+$timeZone")
                            }
                            timeZone < 0 -> {
                                sdf_sunrise.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                            }
                            else -> {
                                sdf_sunrise.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                            }
                        }
                        val formattedDate_sunrise: String = sdf_sunrise.format(date_sunrise)
                        text_sunrise.text = formattedDate_sunrise

                        //Закат
                        val unixSeconds_sunset = (weatherResponse.body()?.sys?.sunset)!!.toLong()
                        val date_sunset = Date(unixSeconds_sunset * 1000L)
                        val sdf_sunset = SimpleDateFormat("HH:mm")
                        when {
                            timeZone > 0 -> {
                                sdf_sunset.timeZone = TimeZone.getTimeZone("GMT+$timeZone")
                            }
                            timeZone < 0 -> {
                                sdf_sunset.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                            }
                            else -> {
                                sdf_sunset.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                            }
                        }
                        val formattedDate_sunset: String = sdf_sunset.format(date_sunset)
                        text_sunset.text = formattedDate_sunset
                    }
                }
            })
        } catch (e: NoInternetException) {
            Snackbar.make(view, "${e.message}", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}