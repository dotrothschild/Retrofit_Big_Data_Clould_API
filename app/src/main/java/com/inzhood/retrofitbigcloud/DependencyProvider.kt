package com.inzhood.retrofitbigcloud

import android.app.Application
import com.inzhood.retrofitbigcloud.bdc.BdcRepository
import com.inzhood.retrofitbigcloud.bdc.BigDataCloudApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Dependency Provider class
class DependencyProvider(application: Application) {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api-bdc.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService: BigDataCloudApi by lazy {
        retrofit.create(BigDataCloudApi::class.java)
    }

    val bdcRepository: BdcRepository by lazy {
        BdcRepository(apiService)
    }
}
