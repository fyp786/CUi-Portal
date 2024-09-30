package com.faa.cuiportal.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faa.cuiportal.Model.ApiResponse
import com.faa.cuiportal.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse

class SignUpViewModel : ViewModel() {

    private val _response = MutableLiveData<ApiResponse>()
    val response: LiveData<ApiResponse> get() = _response

    fun signUp(username: String, email: String, password: String, userType: String) {
        RetrofitInstance.apiService.signUp(username, email, password, userType)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: RetrofitResponse<ApiResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        _response.value = response.body()?.copy(status = "success")
                    } else {
                        _response.value = ApiResponse(
                            status = "error",
                            message = "Error creating account",
                            username = username
                        )
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    _response.value = ApiResponse(
                        status = "error",
                        message = "Error: ${t.message}",
                        username = username
                    )
                }
            })
    }
}
