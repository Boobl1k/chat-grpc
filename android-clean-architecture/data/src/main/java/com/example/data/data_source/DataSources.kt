package com.example.data.data_source

import com.example.core.DataClients.myBookClient
import com.example.domain.data_source.MyBookDataSource

class DataSources {
    companion object {
        val myBookService: MyBookDataSource = myBookClient.create(MyBookDataSource::class.java)
    }
}