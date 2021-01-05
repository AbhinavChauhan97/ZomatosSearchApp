package com.example.zomatorsearchapp.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.zomatorsearchapp.model.Establishment
import com.example.zomatorsearchapp.model.Location
import com.example.zomatorsearchapp.paging.RestaurantsDataSourceFactory
import com.example.zomatorsearchapp.repository.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RestaurantsViewModel:ViewModel() {

    private lateinit var establishmentsDataSourceFactory:RestaurantsDataSourceFactory
    private val pagedListConfig = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()
    lateinit var restaurantsPagedList:LiveData<PagedList<Establishment>>
    var hasResults = false
    suspend fun fetchResultsFor(locationQuery: String):Boolean{

        //viewModelScope.launch (Dispatchers.IO) {
            val location = getLocationSuggestionFor(locationQuery)
            if(location != null){
                val locationId = location.entity_id
                val locationType = location.entity_type
                establishmentsDataSourceFactory = RestaurantsDataSourceFactory(locationId,locationType)
                restaurantsPagedList = LivePagedListBuilder(establishmentsDataSourceFactory,pagedListConfig).build()
                hasResults = true
            }
        return hasResults
    }

    private suspend fun getLocationSuggestionFor(locationQuery:String): Location? {
        val call = RetrofitClient.locationApi.getMatchingLocationsFor(locationQuery)
        val deferred = viewModelScope.async(Dispatchers.IO) {
            call.execute()
        }.await()
        val responseBody = deferred.body()
        if(responseBody != null && deferred.isSuccessful){
           return responseBody.location_suggestions[0]
        }
        return null
    }
}