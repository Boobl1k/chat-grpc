package com.example.android_clean_architecture.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.use_case.TodoUseCase
import com.example.domain.dto.TodoData
import kotlinx.coroutines.launch

class SecondViewModel : ViewModel() {

    private val todoUseCase = TodoUseCase()
    private val todoDataMutableMap = mutableMapOf<Int, MutableLiveData<TodoData>>()

    fun getTodoData(id: Int): MutableLiveData<TodoData> {
        return if (todoDataMutableMap.containsKey(id)) todoDataMutableMap[id]!!
        else {
            val data = MutableLiveData<TodoData>()
            viewModelScope.launch {
                data.postValue(todoUseCase.getTodo(id))
                todoDataMutableMap[id] = data
            }
            data
        }
    }
}