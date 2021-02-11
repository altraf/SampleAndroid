package com.example.sampleandroid.controller

import android.util.Log
import com.example.sampleandroid.api.SearchApi
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class SearchController @Inject constructor() : SearchApi {
    private val host = "itunes.apple.com"
    private val client = OkHttpClient()
    override fun search(searchTerms: String, callback: Callback) {
        val url = HttpUrl.Builder()
            .scheme("https")
            .host(host)
            .addPathSegment("search")
            .addQueryParameter("term", searchTerms)
            .addQueryParameter("media", "music")
            .addQueryParameter("entity", "album")
            .addQueryParameter("attribute", "artistTerm")
            .build()

        Log.d("url", "$url")

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(callback)
    }
}
