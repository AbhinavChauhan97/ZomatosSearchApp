package com.example.zomatorsearchapp.model

data class Location(
    val entity_type:String,
    val entity_id:Int,
    val latitude:Double,
    val longitude:Double,
    val city_id:Int,
    val city_name:String,
)