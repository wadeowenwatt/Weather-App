package com.example.weatherapp

import android.os.Bundle
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

            binding.addButton.setOnClickListener { onClickLocationManageButton() }
            binding.settingButton.setOnClickListener { onClickSettingButton() }

            viewModel.currentData.observe(viewLifecycleOwner) {
                binding.location.text = it.name
            }

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
                when (viewModel.typeWind) {
                    "km/h" -> binding.wind.text = "${it.current.wind_speed} km/h"
                    "mil/h" -> binding.wind.text = "%.2f mil/h".format(it.current.wind_speed * 0.621371192)
                    "m/s" -> binding.wind.text = "%.2f m/s".format(it.current.wind_speed * 0.277777777)
                    else -> binding.wind.text = "%.2f kn".format(it.current.wind_speed * 0.539957)
                }
                binding.volumeOfRain.text =
                    if (it.current.rain != null) it.current.rain.`1h`.toString() + " mm"
                    else "0 mm"
                when (viewModel.typeAtmos) {
                    "mbar" -> binding.pressure.text = "${it.current.pressure} mbar"
                    "atm" -> binding.pressure.text = "%.2f atm".format(it.current.pressure * 0.000986923267)
                    "mmHg" -> binding.pressure.text = "%.2f mmHg".format(it.current.pressure * 0.750061683)
                    "inHg" -> binding.pressure.text = "%.2f inHg".format(it.current.pressure * 0.0295333727)
                    else -> binding.pressure.text = "${it.current.pressure} hPa"
                }
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

            binding.addButton1.setOnClickListener { onClickLocationManageButton() }
            binding.settingButton1.setOnClickListener { onClickSettingButton() }

            viewModel.currentData.observe(viewLifecycleOwner) {
                binding.location1.text = it.name
            }

            // set data with viewModel
            viewModel.weatherData.observe(viewLifecycleOwner) {

                binding.weatherStatus1.text = it.current.weather[0].main

                if (viewModel.typeDegree == "C") {
                    binding.temp1.text = "${(it.current.temp - 273.15).toInt()}"
                } else {
                    binding.temp1.text = "${((it.current.temp - 273.15) * (9 / 5) + 32).toInt()}"
                }

                val iconUrl =
                    "http://openweathermap.org/img/wn/" + it.current.weather[0].icon + "@2x.png"
                val uri = iconUrl.toUri().buildUpon().scheme("https").build()
                binding.iconWeather1.load(uri) {
                    placeholder(R.drawable.placeholdericon)
                }

                // 4 attribute: wind, rain, pressure and humidity
                when (viewModel.typeWind) {
                    "km/h" -> binding.wind1.text = "${it.current.wind_speed} km/h"
                    "mil/h" -> binding.wind1.text = "%.2f mil/h".format(it.current.wind_speed * 0.621371192)
                    "m/s" -> binding.wind1.text = "%.2f m/s".format(it.current.wind_speed * 0.277777777)
                    else -> binding.wind1.text = "%.2f kn".format(it.current.wind_speed * 0.539957)
                }
                binding.chanceOfRain1.text =
                    if (it.current.rain != null) it.current.rain.`1h`.toString() + " mm"
                    else "0 mm"
                when (viewModel.typeAtmos) {
                    "mbar" -> binding.pressure1.text = "${it.current.pressure} mbar"
                    "atm" -> binding.pressure1.text = "%.2f atm".format(it.current.pressure * 0.000986923267)
                    "mmHg" -> binding.pressure1.text = "%.2f mmHg".format(it.current.pressure * 0.750061683)
                    "inHg" -> binding.pressure1.text = "%.2f inHg".format(it.current.pressure * 0.0295333727)
                    else -> binding.pressure1.text = "${it.current.pressure} hPa"
                }
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

    // 2 methods are navigation action button for HomeFragment
    fun onClickSettingButton() {
        // setting button
        findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
    }

    fun onClickLocationManageButton() {
        // location manage button
        findNavController().navigate(R.id.action_homeFragment_to_manageLocationFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}