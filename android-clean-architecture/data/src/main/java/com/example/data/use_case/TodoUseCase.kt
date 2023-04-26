package com.example.data.use_case

import com.example.data.data_source.DataSources
import com.example.domain.dto.TodoData
import com.example.domain.use_case.ITodoUseCase
import retrofit2.awaitResponse

class TodoUseCase : ITodoUseCase {

    override suspend fun getTodo(id: Int): TodoData? {
        if (id == 0) {
            return null
        }
        return DataSources
            .userService
            .getTodoData(id)
            .awaitResponse()
            .body()
    }
}