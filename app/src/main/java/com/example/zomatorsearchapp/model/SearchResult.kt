package com.example.zomatorsearchapp.model

data class SearchResult(
    val results_found:Int,
    val results_shown:Int,
    val results_start:Int,
    val restaurants:List<Establishment>
)