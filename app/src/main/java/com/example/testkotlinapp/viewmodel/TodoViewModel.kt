package com.example.testkotlinapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkotlinapp.data.api.APIHelper
import com.example.testkotlinapp.data.model.ApiUserTodo
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception


class TodoViewModel(private val apiHelper: APIHelper) : ViewModel() {

    private val todoList = MutableLiveData<List<ApiUserTodo>>()
    private val todoListError = MutableLiveData<Boolean>()
    private lateinit var masterJob:Job

    fun fetchData(){
        masterJob = viewModelScope.launch {
            val job = launch {
                todoList.value = null
                todoListError.value = false
                try {
                    val todos: List<ApiUserTodo> = apiHelper.getTodoList()
                    todoList.value = todos
                } catch (e: Exception) {
                    todoListError.value = true
                }
            }
        }
    }

    fun getTodoList(): LiveData<List<ApiUserTodo>> = todoList

    fun getTodoListError() : LiveData<Boolean> = todoListError

    override fun onCleared() {
        masterJob.cancel()
        super.onCleared()
    }

}