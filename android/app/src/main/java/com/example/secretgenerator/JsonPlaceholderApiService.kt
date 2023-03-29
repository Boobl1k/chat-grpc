package com.example.secretgenerator

import okhttp3.*
import java.io.IOException

interface RequestCallback {
    fun onSuccess(response: String)
    fun onFailure(error: String)
}

class JsonPlaceholderApiService {
    private val client = OkHttpClient()

    fun makeRequest(url: String, callback: RequestCallback) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) = e.printStackTrace()

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful)
                        throw Exception("Not successful response: ${response.code} ${response.message}")

                    callback.onSuccess(response.body!!.string())
                }
            }
        })
    }
}

object JsonPlaceholderApi {
    val service: JsonPlaceholderApiService by lazy {
        JsonPlaceholderApiService()
    }
}