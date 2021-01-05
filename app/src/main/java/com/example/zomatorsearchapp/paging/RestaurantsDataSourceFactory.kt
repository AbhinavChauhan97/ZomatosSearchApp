package com.example.zomatorsearchapp.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.zomatorsearchapp.model.Establishment

class RestaurantsDataSourceFactory(val locationId:Int,val locationType:String) : DataSource.Factory<Int,Establishment>() {

    private val establishmentsLiveDataSource = MutableLiveData<com.example.zomatorsearchapp.paging.RestaurantsDataSource>()
    override fun create(): DataSource<Int, Establishment> {

        val dataSource = RestaurantsDataSource(locationId,locationType)
        establishmentsLiveDataSource.postValue(dataSource)
        return dataSource
    }
}