package com.example.android_clean_architecture.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.rabbit.StatisticUpdateConsumer
import com.example.data.use_case.MyBookUseCaseImpl
import com.example.domain.dto.MyBookBooksData
import com.example.domain.dto.StatisticData
import com.example.domain.use_case.MyBookUseCase
import kotlinx.coroutines.launch

class MyBookViewModel : ViewModel() {

    private val useCase: MyBookUseCase = MyBookUseCaseImpl()
    val booksDataMutable = MutableLiveData<List<MyBookBooksData>>()
    val bookDetailsDataMutableMap = mutableMapOf<String, MutableLiveData<MyBookBooksData>>()
    val statisticsDataMutableMap = mutableMapOf<String, MutableLiveData<StatisticData>>()

    fun getBooks(token: String) {
        viewModelScope.launch {
            val books = useCase.getBooks(token)
            booksDataMutable.postValue(books!!)
            books.forEach {
                statisticsDataMutableMap[it.id] = MutableLiveData(StatisticData(it.id, 0))
            }
        }
    }

    fun getBookDetails(token: String, id: String) {
        val mutableData = MutableLiveData<MyBookBooksData>()
        bookDetailsDataMutableMap[id] = mutableData
        viewModelScope.launch {
            mutableData.postValue(useCase.getBookDetails(token, id))
        }
    }

    fun subscribeToStatisticsUpdates() {
        val consumer = StatisticUpdateConsumer()
        viewModelScope.launch {
            consumer.subscribe {
                statisticsDataMutableMap[it.bookId]?.postValue(it)
            }
        }
    }
}