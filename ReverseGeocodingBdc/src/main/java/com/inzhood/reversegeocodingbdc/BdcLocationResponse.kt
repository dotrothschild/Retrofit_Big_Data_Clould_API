package com.inzhood.reversegeocodingbdc

data class BdcLocationResponse(
    val fullResponse: String,
    val country: String,
    val isoCode: String,
    val city: String,
    val displayNameTimeZone: String
    // there are more fields returned, but these are the ones I capture.
)
