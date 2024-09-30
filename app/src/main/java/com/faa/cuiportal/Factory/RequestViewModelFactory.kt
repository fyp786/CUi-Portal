package com.faa.cuiportal.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.faa.cuiportal.Repository.RequestRepository
import com.faa.cuiportal.Retrofit.ApiService

class RequestViewModelFactory(private val repository: RequestRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestViewModel::class.java)) {
            return RequestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
