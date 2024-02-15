package com.inzhood.retrofitbigcloud.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inzhood.retrofitbigcloud.DependencyProvider
import com.inzhood.retrofitbigcloud.R
import com.inzhood.retrofitbigcloud.databinding.FragmentHomeBinding

class HomeFragment  : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // This is added to remove dependency from view model
        val application = requireActivity().application
        val dependencyProvider = DependencyProvider(application)
        val repository = dependencyProvider.bdcRepository
        val homeViewModelFactory = HomeViewModelFactory(repository)
        val homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCityName
        homeViewModel.cityLiveData.observe(viewLifecycleOwner) { location ->
            if (location != null) {
                textView.text = location
                binding.buttonGetCityName.isEnabled = true
            } else {
                // Handle error or loading state
            }
        }
        binding.buttonGetCityName.setOnClickListener {
            textView.text = getString(R.string.fetching_wait)
            binding.buttonGetCityName.isEnabled = false
            try {
                val latitude =binding.latitude.text.toString().toDouble()
                val longitude = binding.longitude.text.toString().toDouble()
                homeViewModel.fetchLocationName(latitude, longitude)
            } catch (e: IllegalArgumentException) {
                homeViewModel.cityLiveData.value = "Enter a valid latitude and longitude"
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}