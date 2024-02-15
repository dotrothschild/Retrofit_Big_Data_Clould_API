package com.inzhood.reversegeocodingbdc

import android.app.Application
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
