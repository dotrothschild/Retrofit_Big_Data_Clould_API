package com.inzhood.retrofitbigcloud.bdc

import androidx.lifecycle.MutableLiveData

class BdcRepository(private val apiService: BigDataCloudApi) {
    suspend fun getLocationData(
        latitude: Double, longitude: Double, apiKey: String,
        cityLiveData: MutableLiveData<String>
    ): BdcLocationResponse? {
        return try {
            val response = apiService.getReverseGeocode(latitude, longitude, "en", apiKey)
            if (response.isSuccessful) {
                // one line only original:  response.body()
                val responseBody = response.body()
                // Extract city from the response body
                val city = responseBody?.city
                // Update MutableLiveData with the city value
                if (city == "") {
                    city.let { cityLiveData.postValue("No city identified") }
                } else {
                    city?.let { cityLiveData.postValue(it) }
                }
                responseBody
            } else {
                null // happens when bad API key submitted
            }
        } catch (e: Exception) {
            e.message
            null
        }
    }
}