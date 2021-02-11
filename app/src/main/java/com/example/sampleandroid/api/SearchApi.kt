package com.example.sampleandroid.api

import okhttp3.Callback

interface SearchApi {
    fun search(searchTerms: String, callback: Callback)
}
