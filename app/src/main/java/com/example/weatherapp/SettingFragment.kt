package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentSettingBinding

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

        binding.button2.setOnClickListener { v: View ->
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

        binding.button3.setOnClickListener { v: View ->
            val popup = PopupMenu(context!!, v)
            popup.menuInflater.inflate(R.menu.popup_wind_unit, popup.menu)
//            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
//
//            }
            popup.show()
        }

        binding.button4.setOnClickListener { v: View ->
            val popup = PopupMenu(context!!, v)
            popup.menuInflater.inflate(R.menu.popup_atmos_unit, popup.menu)
//            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
//
//            }
            popup.show()
        }

    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(context!!, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

//
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}