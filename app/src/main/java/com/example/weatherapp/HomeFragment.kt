package com.example.weatherapp

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.adapter.PredictDailyAdapter
import com.example.weatherapp.adapter.PredictHourlyAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.listener.OnSwipeTouchListener
import com.example.weatherapp.viewmodel.DataViewModel

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by activityViewModels()

    private var isSwipeUp = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnTouchListener(object: OnSwipeTouchListener(requireContext()) {
            override fun onSwipeUp() {
                if (!isSwipeUp){
                    isSwipeUp = true
                    binding.button7Days.visibility = View.GONE
                    binding.listDaily.visibility = View.VISIBLE
                    scaleCard()

                    TransitionManager.beginDelayedTransition(binding.listHour)
                    TransitionManager.beginDelayedTransition(binding.listDaily)

                    bindingData()
                }
            }

            override fun onSwipeDown() {
                if (isSwipeUp) {
                    isSwipeUp = false
                    binding.button7Days.visibility = View.VISIBLE
                    binding.listDaily.visibility = View.GONE
                    scaleCard()

                    TransitionManager.beginDelayedTransition(binding.listHour)
                    TransitionManager.beginDelayedTransition(binding.listDaily)

                    bindingData()
                }
            }
        })

        bindingData()
    }

    private fun bindingData() {

        if (binding.cardLayout.visibility == View.VISIBLE) {
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

//                val iconUrl =
//                    "http://openweathermap.org/img/wn/" + it.current.weather[0].icon + "@2x.png"
//                val uri = iconUrl.toUri().buildUpon().scheme("https").build()
//                binding.iconWeather.load(uri) {
//                    placeholder(R.drawable.placeholdericon)
//                }

                binding.iconWeather.setImageResource(viewModel.getStatusIcon(it.current.weather[0].icon))

                // 4 attribute: wind, rain, pressure and humidity
                when (viewModel.typeWind) {
                    "km/h" -> binding.wind.text = getString(R.string.km_h, it.current.wind_speed)
                    "mil/h" -> binding.wind.text = getString(R.string.mil_h, it.current.wind_speed * 0.621371192)
                    "m/s" -> binding.wind.text = getString(R.string.m_s, it.current.wind_speed * 0.277777777)
                    else -> binding.wind.text = getString(R.string.kn, it.current.wind_speed * 0.539957)
                }
                binding.volumeOfRain.text =
                    if (it.current.rain != null) it.current.rain.`1h`.toString() + " mm"
                    else "0 mm"
                when (viewModel.typeAtmos) {
                    "mbar" -> binding.pressure.text = getString(R.string.mbar, it.current.pressure)
                    "atm" -> binding.pressure.text = getString(R.string.atm, it.current.pressure * 0.000986923267)
                    "mmHg" -> binding.pressure.text = getString(R.string.mmhg, it.current.pressure * 0.750061683)
                    "inHg" -> binding.pressure.text = getString(R.string.inhg, it.current.pressure * 0.0295333727)
                    else -> binding.pressure.text = getString(R.string.hpa, it.current.pressure)
                }
                binding.humidity.text = "${it.current.humidity} %"

                binding.dayInWeek.text = viewModel.convertEpochToDayOfWeek(it.current.dt)
                binding.dayInMonth.text = "${viewModel.convertEpochToDay(it.current.dt)}"

                binding.dayInWeekBold.text = viewModel.convertEpochToDayOfWeek(it.current.dt)
                binding.dayInMonthBold.text = "${viewModel.convertEpochToDay(it.current.dt)}"

                val adapter = PredictHourlyAdapter(it.hourly, viewModel)
                binding.predictHourlyRecycler.adapter = adapter

                val adapterDaily = PredictDailyAdapter(it.daily, viewModel)
                binding.predictDailyRecycler.adapter = adapterDaily
            }

            viewModel.status.observe(viewLifecycleOwner) {
                binding.weatherStatus.text = it
            }

        }

    }

    private fun scaleCard() {
        val root = binding.cardLayout
        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(requireContext(), R.layout.layout_small_card)
        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(requireContext(), R.layout.layout_big_card)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(root)
        if (isSwipeUp) constraintSet1.applyTo(root) else constraintSet2.applyTo(root)
    }

    // 2 methods are navigation action button for HomeFragment
    private fun onClickSettingButton() {
        // setting button
        findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
    }

    private fun onClickLocationManageButton() {
        // location manage button
        findNavController().navigate(R.id.action_homeFragment_to_manageLocationFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}