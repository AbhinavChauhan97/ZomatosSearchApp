package com.example.zomatorsearchapp.api

import com.example.zomatorsearchapp.model.LocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LocationApi {
    @Headers("Accept: application/json", "user-key: 1e34f47c5a2fb41bab6780c0a1f22523")
    @GET("locations")
    fun getMatchingLocationsFor(@Query("query") location: String): Call<LocationResponse>

}