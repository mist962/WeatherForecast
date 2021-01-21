package com.sideki.weatherforecast.view.currentweather

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sideki.weatherforecast.R
import com.sideki.weatherforecast.databinding.FragmentCurrentWeatherBinding
import com.sideki.weatherforecast.model.entities.WeatherDB
import com.sideki.weatherforecast.model.entities.WeatherResponse
import com.sideki.weatherforecast.utils.API_KEY
import com.sideki.weatherforecast.utils.AppQueryTextListener
import com.sideki.weatherforecast.utils.SEARCH_BUNDLE
import com.sideki.weatherforecast.utils.ViewStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment(R.layout.fragment_current_weather), ViewStates {

    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!

    private val currentWeatherViewModel by viewModels<CurrentWeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCurrentWeatherBinding.bind(view)

        search_view.setOnQueryTextListener(AppQueryTextListener {
            loadData(it)
        })

        loadData("Севастополь")
    }

    private fun loadData(query: String) {
        progress_bar.visibility = View.VISIBLE

        currentWeatherViewModel.getCurrentWeather(query, "metric", "ru", API_KEY)

        currentWeatherViewModel.currentWeather.observe(
            viewLifecycleOwner,
            { weatherResponse ->
                weatherResponse.clone().enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(
                        call: Call<WeatherResponse>,
                        response: Response<WeatherResponse>
                    ) {
                        allGoneView()

                        if (response.code() == 404) {
                            cityNotFoundView()
                        } else {
                            binding.apply {
                                showProgressView()
                                //Название города
                                text_city.text = response.body()?.name

                                //Иконка погоды и наименование
                                response.body()?.weather?.forEach {

                                    text_description.text = (it.description).capitalize(Locale.ROOT)

                                    GlobalScope.launch(Dispatchers.IO) {
                                        val drawblweForText =
                                            Glide.with(this@CurrentWeatherFragment)
                                                .asDrawable()
                                                .load("http://openweathermap.org/img/wn/${it.icon}@4x.png")
                                                .submit(100, 100)
                                                .get()
                                        withContext(Dispatchers.Main) {
                                            text_description?.setCompoundDrawablesWithIntrinsicBounds(
                                                null,
                                                drawblweForText,
                                                null,
                                                null
                                            )
                                        }
                                    }
                                }

                                //Температура
                                val temperature = (response.body()?.main?.temp)?.toInt()
                                text_temperature.text = "${temperature.toString()}°"
                                val temperatureFilings =
                                    (response.body()?.main?.feelsLike)?.toInt()
                                text_temperature_filings.text =
                                    "Ощущается как: ${temperatureFilings.toString()}°"

                                //Текущее время города
                                val unixSeconds = (response.body()?.dt)!!.toLong()
                                val date = Date(unixSeconds * 1000L)
                                val sdf = SimpleDateFormat("HH:mm | dd-MM-yy | z")
                                val timeZone = ((response.body()!!.timezone).toInt()) / 3600

                                when {
                                    timeZone > 0 -> {
                                        sdf.timeZone = TimeZone.getTimeZone("GMT+$timeZone")
                                    }
                                    else -> {
                                        sdf.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                                    }
                                }
                                text_date.text = sdf.format(date)

                                text_cloudy.text =
                                    "Облачность: ${(response.body()?.clouds?.all).toString()}%"
                                text_vlaga.text =
                                    "Влажность: ${(response.body()?.main?.humidity).toString()}%"
                                text_vidimost.text =
                                    "Видимость: ${((response.body()?.visibility)?.div(1000)).toString()}км"
                                text_wind_speed.text =
                                    "Скорость ветра: ${(response.body()?.wind?.speed).toString()}м/c"

                                val wind_deg = response.body()?.wind?.deg!!
                                when (wind_deg) {
                                    in 0..90 -> {
                                        text_wint_deg.text = "Напр-е ветра: Восточное"
                                    }
                                    in 91..180 -> {
                                        text_wint_deg.text = "Напр-е ветра: Северное"
                                    }
                                    in 181..270 -> {
                                        text_wint_deg.text = "Напр-е ветра: Западное"
                                    }
                                    else -> {
                                        text_wint_deg.text = "Напр-е ветра: Южное"
                                    }
                                }

                                //Рассвет
                                val unixSecondsSunrise =
                                    (response.body()?.sys?.sunrise)!!.toLong()
                                val dateSunrise = Date(unixSecondsSunrise * 1000L)
                                val sdfSunrise = SimpleDateFormat("HH:mm")
                                when {
                                    timeZone > 0 -> {
                                        sdfSunrise.timeZone = TimeZone.getTimeZone("GMT+$timeZone")
                                    }
                                    else -> {
                                        sdfSunrise.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                                    }
                                }
                                text_sunrise.text = sdfSunrise.format(dateSunrise)

                                //Закат
                                val unixSecondsSunset =
                                    (response.body()?.sys?.sunset)!!.toLong()
                                val dateSunset = Date(unixSecondsSunset * 1000L)
                                val sdfSunset = SimpleDateFormat("HH:mm")
                                when {
                                    timeZone > 0 -> {
                                        sdfSunset.timeZone = TimeZone.getTimeZone("GMT+$timeZone")
                                    }
                                    else -> {
                                        sdfSunset.timeZone = TimeZone.getTimeZone("GMT$timeZone")
                                    }
                                }
                                text_sunset.text = sdfSunset.format(dateSunset)

                                //Сохраняем в БД
                                saveToDB(response.body())
                            }
                            isSuccessfulView()
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        Snackbar.make(requireView(), "${t.message}", Snackbar.LENGTH_SHORT).show()
                        noInternetView()
                    }
                })
            })
    }

    fun saveToDB(remoteResponse: WeatherResponse?) {

        var b = "02d"

        remoteResponse!!.weather.forEach {
            b = it.icon
        }

        GlobalScope.launch(Dispatchers.IO) {
            val a: Bitmap = Glide.with(this@CurrentWeatherFragment)
                .asBitmap()
                .load("http://openweathermap.org/img/wn/${b}@4x.png")
                .submit(100, 100)
                .get()

            val weatherDB = WeatherDB(
                id = 0,
                cityName = remoteResponse.name,
                temperature = (remoteResponse.main.temp).toInt(),
                image = a
            )
            currentWeatherViewModel.addWeather(weatherDB)
        }
    }

    override fun noInternetView() {
        text_city_not_found?.visibility = View.GONE
        constraint_main?.visibility = View.GONE
        progress_bar?.visibility = View.GONE
        text_no_internet?.visibility = View.VISIBLE
    }

    override fun isSuccessfulView() {
        text_no_internet?.visibility = View.GONE
        text_city_not_found?.visibility = View.GONE
        progress_bar?.visibility = View.GONE
        constraint_main?.visibility = View.VISIBLE
    }

    override fun cityNotFoundView() {
        text_no_internet?.visibility = View.GONE
        constraint_main?.visibility = View.GONE
        progress_bar?.visibility = View.GONE
        text_city_not_found?.visibility = View.VISIBLE
    }

    override fun allGoneView() {
        text_no_internet?.visibility = View.GONE
        text_city_not_found?.visibility = View.GONE
        constraint_main?.visibility = View.GONE
        progress_bar?.visibility = View.GONE
    }

    override fun showProgressView() {
        text_no_internet?.visibility = View.GONE
        constraint_main?.visibility = View.GONE
        text_city_not_found?.visibility = View.GONE
        progress_bar?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}