package com.faa.cuiportal.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faa.cuiportal.Model.ApiResponse
import com.faa.cuiportal.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse

class ComplaintsViewModel : ViewModel() {

    private val _response = MutableLiveData<ApiResponse>()
    val response: LiveData<ApiResponse> get() = _response

    fun newRequest(title: String, description: String, location: String, roomNumber: String, username: String) {
        RetrofitInstance.apiService.newRequest("new_request", title, description, location, roomNumber, username)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: RetrofitResponse<ApiResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        _response.value = response.body()
                    } else {
                        Log.e("API Error", "Error response code: ${response.code()}")
                        _response.value = ApiResponse("Error creating request")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("API Error", "API call failed: ${t.message}")
                    _response.value = ApiResponse("Error: ${t.message}")
                }
            })
    }
}
