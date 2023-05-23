package com.example.domain.use_case

import com.example.domain.dto.MyBookBooksData
import com.example.domain.dto.MyBookTokenData

interface MyBookUseCase {
    suspend fun login(username: String, password: String): MyBookTokenData?

    suspend fun getBooks(token: String): List<MyBookBooksData>?
    suspend fun getBookDetails(token: String, id: String): MyBookBooksData?
}