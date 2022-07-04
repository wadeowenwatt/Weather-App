package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.adapter.LocationManageAdapter
import com.example.weatherapp.dataCurrent.CurrentWeather
import com.example.weatherapp.databinding.FragmentManageLocationBinding

class ManageLocationFragment : Fragment() {

    private var _binding : FragmentManageLocationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DataViewModel by activityViewModels()

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

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //Log.e("onQueryTextChange", "called");
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do your task here
                if (query != null) {
                    viewModel.q = query
                    viewModel.getSearchData()
                }
                return false
            }
        })

        viewModel.listSearchData.observe(viewLifecycleOwner) {
            val adapter = viewModel.listSearchData.value?.let { LocationManageAdapter(it) }
            binding.listCity.adapter = adapter
        }

    }

}