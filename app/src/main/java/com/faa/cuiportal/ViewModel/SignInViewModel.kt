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

class SignInViewModel : ViewModel() {

    private val _response = MutableLiveData<ApiResponse>()
    val response: LiveData<ApiResponse> get() = _response

    fun login(email: String, password: String) {
        RetrofitInstance.apiService.login(email, password)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: RetrofitResponse<ApiResponse>) {
                    if (response.isSuccessful) {
                        Log.d("LoginResponse", "Response body: ${response.body()}")
                        _response.value = response.body()
                    } else {
                        Log.e("LoginError", "Error response code: ${response.code()}")
                        _response.value = ApiResponse("Invalid credentials")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("LoginError", "API call failed: ${t.message}")
                    _response.value = ApiResponse("Error: ${t.message}")
                }
            })
    }
}
