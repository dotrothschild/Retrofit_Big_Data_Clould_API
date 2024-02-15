package com.inzhood.reversegeocodingbdc


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BigDataCloudApi {
    @GET("data/reverse-geocode-with-timezone")
    suspend fun getReverseGeocode(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("localityLanguage") localityLanguage: String,
        @Query("key") key: String // for security, this should not be including the API key here, but...
    ): Response<BdcLocationResponse>
}

