package com.faa.cuiportal.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faa.cuiportal.Model.Request
import com.faa.cuiportal.Repository.RequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RequestViewModel(private val repository: RequestRepository) : ViewModel() {

    private val _requests = MutableLiveData<List<Request>>()
    val requests: LiveData<List<Request>> get() = _requests

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Fetch new requests
    fun fetchNewRequests() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNewRequests(
                onResult = { fetchedRequests ->
                    Log.d("RequestViewModel", "Fetched requests: $fetchedRequests")
                    _requests.postValue(fetchedRequests.filter { it.status == "new" })
                },
                onError = { errorMessage ->
                    Log.e("RequestViewModel", "Error: $errorMessage")
                    _error.postValue(errorMessage)
                }
            )
        }
    }

    // Fetch complaints based on staff email
    fun getComplaintsByStaffEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchComplaintsByEmail(email,
                onResult = { complaints ->
                    Log.d("RequestViewModel", "Fetched complaints: $complaints")
                    _requests.postValue(complaints)
                },
                onError = { errorMessage ->
                    Log.e("RequestViewModel", "Error fetching complaints: $errorMessage")
                    _error.postValue(errorMessage)
                }
            )
        }
    }
}
