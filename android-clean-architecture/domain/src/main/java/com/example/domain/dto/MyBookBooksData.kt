package com.example.domain.dto

class MyBookBooksData(
    val id: String,
    val title: String,
    val author: MyBookAuthor,
    val description: String,
    val genre: String
)

class MyBookAuthor(
    val id: String,
    val fullName: String,
    val description: String
)
