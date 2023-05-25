package com.example.android_clean_architecture.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.use_case.ChatUseCaseImpl
import com.example.domain.dto.ChatMessageData
import com.example.domain.use_case.ChatUseCase
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val useCase: ChatUseCase = ChatUseCaseImpl()
    val messagesDataMutable = MutableLiveData<List<ChatMessageData>>(mutableListOf())
    fun sendMessage(token: String, text: String) {
        viewModelScope.launch {
            useCase.sendMessage(token, text)
        }
    }

    fun getMessages(token: String) {
        messagesDataMutable.postValue(listOf())
        viewModelScope.launch {
            useCase.getMessages(token) {
                messagesDataMutable.postValue(messagesDataMutable.value!! + listOf(it))
            }
        }
    }
}