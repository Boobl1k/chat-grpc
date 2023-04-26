package com.example.domain.use_case

import com.example.domain.dto.TodoData

interface ITodoUseCase {
    suspend fun getTodo(id: Int): TodoData?
}