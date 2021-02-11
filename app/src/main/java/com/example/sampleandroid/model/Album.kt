package com.example.sampleandroid.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Album(
    val artworkUrl100: String,
    val collectionName: String,
    val releaseDate: Date,
    val primaryGenreName: String,
    val collectionPrice: Double,
    val currency: String,
    val copyright: String
)
