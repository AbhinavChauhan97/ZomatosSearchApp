package com.example.zomatorsearchapp.paging

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.zomatorsearchapp.model.Establishment
import com.example.zomatorsearchapp.model.SearchResult
import com.example.zomatorsearchapp.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RestaurantsDataSource(val locationId:Int,val locationType:String) : PositionalDataSource<Establishment>() {
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<Establishment>
    ) {
        val position = computeInitialLoadPosition(params,15)
        val loadSize = computeInitialLoadSize(params,position,15)

        val call = RetrofitClient.searchApi.searchRestaurantsInLocation(
         locationId = locationId,
         locationType = locationType,
         fetchStart = position,
         fetchCount = loadSize
        )

        call.enqueue(object : Callback<SearchResult>{
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val responseBody = response.body()
                Log.d("log",responseBody.toString())
                if(response.isSuccessful && responseBody != null){
                    callback.onResult(responseBody.restaurants,position,loadSize)
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d("log",t.stackTraceToString())
            }
        })

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Establishment>) {


        val call = RetrofitClient.searchApi.searchRestaurantsInLocation(
            locationId = locationId,
            locationType = locationType,
            fetchStart = params.startPosition,
            fetchCount = params.loadSize
        )
        call.enqueue(object : Callback<SearchResult>{
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val responseBody = response.body()
                Log.d("log",responseBody.toString())
                if(response.isSuccessful && responseBody != null){
                    callback.onResult(responseBody.restaurants)
                }
            }
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.d("log",t.stackTraceToString())
            }

        })
    }

}