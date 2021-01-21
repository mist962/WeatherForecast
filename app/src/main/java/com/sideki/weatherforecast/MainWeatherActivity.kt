package com.sideki.weatherforecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sideki.weatherforecast.databinding.ActivityMainWeatherBinding
import com.sideki.weatherforecast.view.currentweather.CurrentWeatherFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main_weather.*

@AndroidEntryPoint
class MainWeatherActivity : AppCompatActivity() {

    private var _binding: ActivityMainWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainWeatherBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        binding.apply {
            bottom_nav.setupWithNavController(findNavController(R.id.fragment))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}