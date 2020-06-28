package com.example.testkotlinapp.data.model


data class ApiUserTodo(
    val userId: Int = 0,
    val id:Int = 0,
    val title:String = "",
    val completed:Boolean = false
)