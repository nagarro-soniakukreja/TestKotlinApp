package com.example.testkotlinapp.data.api

class APIHelperImp(private val apiService: APIService): APIHelper {
    override suspend fun getTodoList() = apiService.getTodoList()

}