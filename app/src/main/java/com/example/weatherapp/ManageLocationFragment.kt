package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.adapter.LocationManageAdapter
import com.example.weatherapp.databinding.FragmentManageLocationBinding
import com.example.weatherapp.viewmodel.DataViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

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

        binding.searchView.queryHint = "Search Your City"

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                //Log.e("onQueryTextChange", "called");
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do your task here
                if (query != null) {
                    runBlocking {
                        viewModel.getSearchData(query)
                        delay(500)
                    }
                    bindingData()
                }
                return false
            }
        })
        bindingData()
    }

    private fun bindingData() {
        viewModel.listSearchData.observe(viewLifecycleOwner) {
            val adapter = LocationManageAdapter(it, viewModel)
            binding.listCity.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}