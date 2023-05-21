package com.example.android_clean_architecture.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.use_case.MyBookUseCaseImpl
import com.example.domain.dto.MyBookTokenData
import com.example.domain.use_case.MyBookUseCase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val myBookUseCase : MyBookUseCase = MyBookUseCaseImpl()
    val myBookTokenDataMutable = MutableLiveData<MyBookTokenData?>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            myBookTokenDataMutable.postValue(myBookUseCase.login(username, password))
        }
    }
}