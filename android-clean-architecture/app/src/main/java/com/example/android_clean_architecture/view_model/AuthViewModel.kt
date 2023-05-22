package com.example.android_clean_architecture.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.use_case.ChatUseCaseImpl
import com.example.data.use_case.MyBookUseCaseImpl
import com.example.domain.dto.ChatTokenData
import com.example.domain.dto.MyBookTokenData
import com.example.domain.use_case.ChatUseCase
import com.example.domain.use_case.MyBookUseCase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val myBookUseCase : MyBookUseCase = MyBookUseCaseImpl()
    private val chatUseCase : ChatUseCase = ChatUseCaseImpl()
    val myBookTokenDataMutable = MutableLiveData<MyBookTokenData?>()
    val chatTokenDataMutable = MutableLiveData<ChatTokenData?>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            myBookTokenDataMutable.postValue(myBookUseCase.login(username, password))
            chatTokenDataMutable.postValue(chatUseCase.login(username, password))
        }
    }
}