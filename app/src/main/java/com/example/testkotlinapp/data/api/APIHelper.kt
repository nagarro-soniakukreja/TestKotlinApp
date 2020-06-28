package com.example.testkotlinapp.data.api

import com.example.testkotlinapp.data.model.ApiUserTodo

interface APIHelper{
    suspend fun getTodoList(): List<ApiUserTodo>
}