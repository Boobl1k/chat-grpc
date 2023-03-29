package com.example.domain.dto

import kotlinx.serialization.Serializable

@Serializable
data class TodoData(
    val id: Int = 0,
    val title: String = ""
)
