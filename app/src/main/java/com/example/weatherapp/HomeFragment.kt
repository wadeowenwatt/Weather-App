package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.weatherapp.adapter.PredictHourlyAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
        }

        viewModel.weatherData.observe(viewLifecycleOwner) {
            binding.location.text = it.timezone
            binding.weatherStatus.text = it.current.weather[0].main
            binding.temp.text = "${(it.current.temp - 273.15).toInt()}"
            val iconUrl = "http://openweathermap.org/img/wn/" + it.current.weather[0].icon + "@2x.png"
            val uri = iconUrl.toUri().buildUpon().scheme("https").build()
            binding.iconWeather.load(uri) {
                placeholder(R.drawable.placeholdericon)
            }

            binding.wind.text = "${it.current.wind_speed} km/h"
            binding.chanceOfRain.text =
                if (it.current.rain != null) it.current.rain.`1h`.toString() + " mm"
                    else "0 mm"
            binding.pressure.text = "${it.current.pressure} mbar"
            binding.humidity.text = "${it.current.humidity} %"

            binding.dayInWeek.text = "${viewModel.convertEpochToDayOfWeek(it.current.dt)}"
            binding.dayInMonth.text = "${viewModel.convertEpochToDay(it.current.dt)}"

            binding.dayInWeekBold.text = "${viewModel.convertEpochToDayOfWeek(it.current.dt)}"
            binding.dayInMonthBold.text = "${viewModel.convertEpochToDay(it.current.dt)}"

            val adapter = PredictHourlyAdapter(it.hourly)
            binding.predictHourlyRecycler.adapter = adapter
        }

        viewModel.status.observe(viewLifecycleOwner) {
            binding.weatherStatus.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}