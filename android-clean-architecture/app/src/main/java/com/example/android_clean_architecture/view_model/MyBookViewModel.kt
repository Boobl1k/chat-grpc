package com.example.android_clean_architecture.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.use_case.MyBookUseCaseImpl
import com.example.domain.dto.MyBookBooksData
import com.example.domain.use_case.MyBookUseCase
import kotlinx.coroutines.launch

class MyBookViewModel : ViewModel() {

    private val useCase: MyBookUseCase = MyBookUseCaseImpl()
    val booksDataMutable = MutableLiveData<List<MyBookBooksData>>()

    fun getBooks(token: String) {
        viewModelScope.launch {
            booksDataMutable.postValue(useCase.getBooks(token))
        }
    }
}