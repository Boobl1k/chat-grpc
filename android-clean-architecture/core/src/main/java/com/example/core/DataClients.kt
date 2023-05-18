package com.example.core

import com.apollographql.apollo3.ApolloClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataClients {
    private const val myBookUrl = "http://10.0.2.2:5100"

    val retrofitClient: Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myBookClient: Retrofit = Retrofit.Builder()
        .baseUrl(myBookUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myBookGraphQlClient : ApolloClient = ApolloClient.Builder()
        .serverUrl("$myBookUrl/graphql")
        .build()
}