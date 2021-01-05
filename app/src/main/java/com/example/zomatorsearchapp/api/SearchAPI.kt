package com.example.zomatorsearchapp.api

import com.example.zomatorsearchapp.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchAPI {

    @Headers("Accept: application/json", "user-key:1e34f47c5a2fb41bab6780c0a1f22523")
    @GET("search")
    fun searchRestaurantsInLocation(@Query("entity_id") locationId: Int,
                                    @Query("entity_type") locationType: String,
                                    @Query("collection_id") collectionId:String? = null,
                                    @Query("q") query:String? = null,
                                    @Query("start") fetchStart: Int = 0,
                                    @Query("count") fetchCount: Int = 20,
                                    @Query("lat") latitude: Float? = null,
                                    @Query("lon") longitude: Float? = null,
                                    @Query("radius") searchRadius:Double? = null,
                                    @Query("cuisines") cuisines:String? = null,
                                    @Query("establishment_type") establishmentType:String? = null,
                                    @Query("category") category:String? = null,
                                    @Query("sort") sortBy:String? = null,
                                    @Query("order") orderBy:String? = null
    ): Call<SearchResult>
}