package com.example.data.use_case

import com.example.core.DataClients.myBookGraphQlClient
import com.example.data.BooksQuery
import com.example.data.data_source.DataSources
import com.example.domain.dto.MyBookAuthor
import com.example.domain.dto.MyBookBooksData
import com.example.domain.use_case.MyBookUseCase
import retrofit2.awaitResponse

class MyBookUseCaseImpl : MyBookUseCase {
    override suspend fun login(username: String, password: String) =
        DataSources.myBookService.login(username, password).awaitResponse().run {
            if (isSuccessful) body() else null
        }

    override suspend fun getBooks(token: String): List<MyBookBooksData>? =
        myBookGraphQlClient.query(BooksQuery()).addHttpHeader("Authorization", "Bearer $token").execute().data?.booksList?.map {
            MyBookBooksData(it.id.toString(), it.title, MyBookAuthor(it.author.id.toString(), it.author.fullName, it.author.description), it.description, it.genre)
        }
}