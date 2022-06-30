package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.adapter.LocationManageAdapter
import com.example.weatherapp.dataCurrent.CurrentWeather
import com.example.weatherapp.databinding.FragmentManageLocationBinding

class ManageLocationFragment : Fragment() {

    private var _binding : FragmentManageLocationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by activityViewModels()

    private lateinit var listSearchData : MutableList<CurrentWeather>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageLocationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_manageLocationFragment_to_homeFragment)
        }

        binding.searchView.isSubmitButtonEnabled = true

        viewModel.searchData.observe(viewLifecycleOwner) {
            listSearchData= mutableListOf(it)
            val adapter = LocationManageAdapter(listSearchData)
            binding.listCity.adapter = adapter
        }

    }

}