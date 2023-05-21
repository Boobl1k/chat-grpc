package com.example.data.data_source

import com.example.core.DataClients.myBookClient
import com.example.core.DataClients.retrofitClient
import com.example.domain.data_source.ITodoDataSource
import com.example.domain.data_source.MyBookDataSource

class DataSources {
    companion object {
        val userService: ITodoDataSource = retrofitClient.create(ITodoDataSource::class.java)
        val myBookService: MyBookDataSource = myBookClient.create(MyBookDataSource::class.java)
    }
}