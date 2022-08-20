package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentSettingBinding
import com.example.weatherapp.network.FileConfig.NIGHT_MODE
import com.example.weatherapp.viewmodel.DataViewModel
import java.text.SimpleDateFormat
import java.util.*

class SettingFragment : Fragment() {

    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_homeFragment)
        }

        binding.tempUnit.setOnClickListener { v: View ->
            val popup = PopupMenu(context!!, v)
            popup.menuInflater.inflate(R.menu.popup_temp_unit, popup.menu)
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.option_1 -> viewModel.typeDegree = "C"
                    else -> viewModel.typeDegree = "F"
                }
                true
            }
            popup.show()
        }

        binding.windUnit.setOnClickListener { v: View ->
            val popup = PopupMenu(context!!, v)
            popup.menuInflater.inflate(R.menu.popup_wind_unit, popup.menu)
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.wind_1 -> viewModel.typeWind = "km/h"
                    R.id.wind_2 -> viewModel.typeWind = "mil/h"
                    R.id.wind_3 -> viewModel.typeWind = "m/s"
                    else -> viewModel.typeWind = "kn"
                }
                true
            }
            popup.show()
        }

        binding.atmosUnit.setOnClickListener { v: View ->
            val popup = PopupMenu(context!!, v)
            popup.menuInflater.inflate(R.menu.popup_atmos_unit, popup.menu)
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.atmos_1 -> viewModel.typeAtmos = "mbar"
                    R.id.atmos_2 -> viewModel.typeAtmos = "atm"
                    R.id.atmos_3 -> viewModel.typeAtmos = "mmHg"
                    R.id.atmos_4 -> viewModel.typeAtmos = "inHg"
                    else -> viewModel.typeWind = "hPa"
                }
                true
            }
            popup.show()
        }

        binding.themeUnit.setOnClickListener { v: View ->
            val popup = PopupMenu(context!!, v)
            popup.menuInflater.inflate(R.menu.popup_theme, popup.menu)
            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                when (menuItem.itemId) {
                    R.id.theme_1 -> {
                        NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_NO
                        AppCompatDelegate.setDefaultNightMode(NIGHT_MODE)
                    }
                    R.id.theme_2 -> {
                        NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_YES
                        AppCompatDelegate.setDefaultNightMode(NIGHT_MODE)
                    }
                    R.id.theme_3 -> {
                        val date = Calendar.getInstance().time
                        val time = SimpleDateFormat("kk").format(date)
                        if (time.toInt() > 18) {
                            NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_YES
                            AppCompatDelegate.setDefaultNightMode(NIGHT_MODE)
                        } else {
                            NIGHT_MODE = AppCompatDelegate.MODE_NIGHT_NO
                            AppCompatDelegate.setDefaultNightMode(NIGHT_MODE)
                        }
                    }
                }
                true
            }
            popup.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}