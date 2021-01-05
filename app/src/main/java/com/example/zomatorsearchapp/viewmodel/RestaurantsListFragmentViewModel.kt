package com.example.zomatorsearchapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.zomatorsearchapp.api.LocationApi
import com.example.zomatorsearchapp.api.SearchAPI
import com.example.zomatorsearchapp.model.Establishment
import com.example.zomatorsearchapp.model.LocationResponse
import com.example.zomatorsearchapp.model.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantsListFragmentViewModel : ViewModel() {

    var inGivenLocationRestaurantResponse:((Boolean) -> Unit)? = null
    lateinit var restaurants: List<Establishment>
    var containsRestaurants = false
    val retrofit = Retrofit.Builder()
        .baseUrl("https://developers.zomato.com/api/v2.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun searchMatchingLocationsFor(locationSearchQuery: String) {
        val locationAPI = retrofit.create(LocationApi::class.java)
        val locationCall = locationAPI.getMatchingLocationsFor(locationSearchQuery)
        locationCall.enqueue(object : Callback<LocationResponse>{
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                if(response.isSuccessful) {
                    val searchAPI = retrofit.create(SearchAPI::class.java)
                    val locations = response.body()?.location_suggestions
                    val matchedLocation = locations?.get(0)
                    val locationType = matchedLocation?.entity_type
                    val locationId = matchedLocation?.entity_id
                    val searchCall = searchAPI.searchRestaurantsInLocation(locationId!!,locationType!!)
                    searchCall.enqueue(object : Callback<SearchResult>{
                        override fun onResponse(
                            call: Call<SearchResult>,
                            response: Response<SearchResult>
                        ) {
                            if(response.isSuccessful) {
                                containsRestaurants = true
                                restaurants = response.body()!!.restaurants
                                inGivenLocationRestaurantResponse?.invoke(true)
                            }
                            else{
                                inGivenLocationRestaurantResponse?.invoke(false)
                            }
                        }
                        override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                            inGivenLocationRestaurantResponse?.invoke(false)
                        }
                    })
                }
            }
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Log.d("log",t.stackTraceToString())
            }
        })
    }
    fun whenInGivenLocationRestaurantResponseReceived(locationSearchQuery: String, doOnResponse:(isSuccessful:Boolean) -> Unit){
        this.inGivenLocationRestaurantResponse = doOnResponse
        searchMatchingLocationsFor(locationSearchQuery)
    }
}