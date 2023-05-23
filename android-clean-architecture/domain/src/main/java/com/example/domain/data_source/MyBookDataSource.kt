package com.example.domain.data_source

import com.example.domain.dto.MyBookBooksData
import com.example.domain.dto.MyBookTokenData
import retrofit2.Call
import retrofit2.http.*

interface MyBookDataSource {
    @FormUrlEncoded
    @POST("Auth/Login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password"
    ): Call<MyBookTokenData>

    @GET("Catalog/GetBook/{id}")
    fun getBookDetails(@Header("Authorization") authHeader: String, @Path("id") id: String): Call<MyBookBooksData>
}