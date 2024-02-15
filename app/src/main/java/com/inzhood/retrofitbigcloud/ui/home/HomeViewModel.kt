package com.inzhood.retrofitbigcloud.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.inzhood.retrofitbigcloud.BuildConfig
import com.inzhood.retrofitbigcloud.bdc.BdcLocationResponse
import com.inzhood.retrofitbigcloud.bdc.BdcRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BdcRepository) : ViewModel() {
//class HomeViewModel : ViewModel() {
    private val apiKeyValue = MutableLiveData<String>()
    init {

        val apiKey = BuildConfig.BDC_API_KEY
        apiKeyValue.value = apiKey
    }
    private val _locationData = MutableLiveData<BdcLocationResponse?>()
    val locationData = MutableLiveData<BdcLocationResponse?>()
    val cityLiveData = MutableLiveData<String>()
    fun fetchLocationName(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val result = repository.getLocationData(latitude, longitude, apiKeyValue.value!!, cityLiveData)
            if (result != null) {
                _locationData.value = result
            } else {
               cityLiveData.value = "Nothing was returned, check your API key in the gradle.properties file"
            }

        }
    }
}
class HomeViewModelFactory(private val repository: BdcRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unsupported ViewModel class")
    }
}