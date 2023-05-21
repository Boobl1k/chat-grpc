package com.example.android_clean_architecture.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.use_case.TodoUseCase
import com.example.domain.dto.TodoData
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val todoUseCase = TodoUseCase()
    val todoDataMutable = MutableLiveData<TodoData?>()

    fun getTodoData(id: Int) {
        viewModelScope.launch {
            todoDataMutable.postValue(todoUseCase.getTodo(id))
        }
    }
}