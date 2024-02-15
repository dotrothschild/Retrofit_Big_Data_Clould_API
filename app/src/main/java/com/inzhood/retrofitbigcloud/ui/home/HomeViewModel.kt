package com.inzhood.retrofitbigcloud.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inzhood.retrofitbigcloud.BuildConfig
import com.inzhood.retrofitbigcloud.bdc.BdcLocationResponse
import com.inzhood.retrofitbigcloud.bdc.BdcRepository
import com.inzhood.retrofitbigcloud.bdc.BigDataCloudApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {
    private val apiKeyValue = MutableLiveData<String>()
    init {

        val apiKey = BuildConfig.BDC_API_KEY
        apiKeyValue.value = apiKey
    }
    // this is considered poor programming practice, apiService and repository should not be in viewmodel
    private val apiService: BigDataCloudApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-bdc.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(BigDataCloudApi::class.java)
    }

    private val repository by lazy {
        BdcRepository(apiService)
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