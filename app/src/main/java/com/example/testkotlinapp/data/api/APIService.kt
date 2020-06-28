package com.example.testkotlinapp.data.api

import com.example.testkotlinapp.data.model.ApiUserTodo
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService{
    @GET("todos/")
    suspend fun getTodoList(): List<ApiUserTodo>
}