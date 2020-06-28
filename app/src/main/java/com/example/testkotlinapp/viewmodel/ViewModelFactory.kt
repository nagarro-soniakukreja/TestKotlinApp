package com.example.testkotlinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testkotlinapp.data.api.APIHelper

class ViewModelFactory(private val apiHelper: APIHelper): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(apiHelper) as T
    }

}