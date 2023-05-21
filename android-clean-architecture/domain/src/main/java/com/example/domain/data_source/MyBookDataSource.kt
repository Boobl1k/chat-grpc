package com.example.domain.data_source

import com.example.domain.dto.MyBookTokenData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyBookDataSource {
    @FormUrlEncoded
    @POST("Auth/Login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = "password"
    ): Call<MyBookTokenData>
}