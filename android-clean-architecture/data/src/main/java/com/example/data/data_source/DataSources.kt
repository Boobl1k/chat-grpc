package com.example.data.data_source

import com.example.core.RetrofitClient.Companion.retrofitClient
import com.example.domain.data_source.ITodoDataSource

class DataSources {
    companion object {
        val userService: ITodoDataSource = retrofitClient.create(ITodoDataSource::class.java)
    }
}