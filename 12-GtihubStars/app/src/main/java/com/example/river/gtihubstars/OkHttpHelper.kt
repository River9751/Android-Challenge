package com.example.river.gtihubstars

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray


class OkHttpHelper {

    companion object {

        fun getResponse(url: String): JSONArray {

            val client = OkHttpClient()

            val requestBuilder = Request.Builder()

            val request = requestBuilder
                .url(url)
                .build()

            val response = client.newCall(request).execute()

            val result = response.body()!!.string()

            return JSONArray(result)
        }
    }
}