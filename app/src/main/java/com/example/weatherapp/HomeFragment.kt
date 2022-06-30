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
import com.example.weatherapp.adapter.PredictDailyAdapter
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

        binding.button7Days.setOnClickListener {
            binding.bigCard.visibility = View.GONE
            binding.smallCard.visibility = View.VISIBLE
            binding.button7Days.visibility = View.GONE
            binding.listDaily.visibility = View.VISIBLE
            bindingData()
        }
        bindingData()
    }

    private fun bindingData() {

        if (binding.bigCard.visibility == View.VISIBLE) {
            // setting button
            binding.settingButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
            }
            binding.addButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_manageLocationFragment)
            }

            viewModel.currentData.observe(viewLifecycleOwner) {
                binding.location.text = it.name
            }
            // set data with viewModel
            viewModel.weatherData.observe(viewLifecycleOwner) {

                binding.weatherStatus.text = it.current.weather[0].main

                if (viewModel.typeDegree == "C") {
                    binding.temp.text = "${(it.current.temp - 273.15).toInt()}"
                } else {
                    binding.temp.text = "${((it.current.temp - 273.15) * (9 / 5) + 32).toInt()}"
                }

                val iconUrl =
                    "http://openweathermap.org/img/wn/" + it.current.weather[0].icon + "@2x.png"
                val uri = iconUrl.toUri().buildUpon().scheme("https").build()
                binding.iconWeather.load(uri) {
                    placeholder(R.drawable.placeholdericon)
                }

                // 4 attribute: wind, rain, pressure and humidity
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

        if (binding.smallCard.visibility == View.VISIBLE){
            // setting button
            binding.settingButton1.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
            }
            binding.addButton1.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_manageLocationFragment)
            }

            viewModel.currentData.observe(viewLifecycleOwner) {
                binding.location1.text = it.name
            }

            // set data with viewModel
            viewModel.weatherData.observe(viewLifecycleOwner) {

                binding.weatherStatus1.text = it.current.weather[0].main

                binding.temp1.text = "${(it.current.temp - 273.15).toInt()}"

                val iconUrl =
                    "http://openweathermap.org/img/wn/" + it.current.weather[0].icon + "@2x.png"
                val uri = iconUrl.toUri().buildUpon().scheme("https").build()
                binding.iconWeather1.load(uri) {
                    placeholder(R.drawable.placeholdericon)
                }

                // 4 attribute: wind, rain, pressure and humidity
                binding.wind1.text = "${it.current.wind_speed} km/h"
                binding.chanceOfRain1.text =
                    if (it.current.rain != null) it.current.rain.`1h`.toString() + " mm"
                    else "0 mm"
                binding.pressure1.text = "${it.current.pressure} mbar"
                binding.humidity1.text = "${it.current.humidity} %"

                binding.dayInWeek1.text = "${viewModel.convertEpochToDayOfWeek(it.current.dt)}"
                binding.dayInMonth1.text = "${viewModel.convertEpochToDay(it.current.dt)}"

                binding.dayInWeekBold.text = "${viewModel.convertEpochToDayOfWeek(it.current.dt)}"
                binding.dayInMonthBold.text = "${viewModel.convertEpochToDay(it.current.dt)}"

                val adapter = PredictHourlyAdapter(it.hourly)
                binding.predictHourlyRecycler.adapter = adapter

                val adapterDaily = PredictDailyAdapter(it.daily)
                binding.predictDailyRecycler.adapter = adapterDaily
            }

            viewModel.status.observe(viewLifecycleOwner) {
                binding.weatherStatus1.text = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}