package com.example.zomatorsearchapp.repository

import com.example.zomatorsearchapp.api.LocationApi
import com.example.zomatorsearchapp.api.SearchAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val baseUrl = "https://developers.zomato.com/api/v2.1/"
    private val instance = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val locationApi: LocationApi = instance.create(LocationApi::class.java)
    val searchApi: SearchAPI = instance.create(SearchAPI::class.java)

}

