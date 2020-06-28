package com.example.testkotlinapp.data.api

import com.example.testkotlinapp.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder{
    private val client = OkHttpClient.Builder().build()

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()

    val apiService = retrofit.create(APIService::class.java)
}