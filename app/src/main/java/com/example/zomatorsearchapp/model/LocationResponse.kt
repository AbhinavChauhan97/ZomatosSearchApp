package com.example.zomatorsearchapp.model

data class LocationResponse(
    val location_suggestions:List<Location>,
    val status:String,
    val has_more:Int,
    val has_total:Int,
    val user_has_addresses:Boolean
)