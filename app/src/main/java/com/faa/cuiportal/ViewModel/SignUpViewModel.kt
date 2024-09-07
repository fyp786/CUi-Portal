package com.faa.cuiportal.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faa.cuiportal.Model.Response
import com.faa.cuiportal.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse

// SignUpViewModel.kt
class SignUpViewModel : ViewModel() {

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> get() = _response

    fun signUp(username: String, email: String, password: String, userType: String) {
        RetrofitInstance.apiService.signUp(username, email, password, userType)
            .enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: RetrofitResponse<Response>) {
                    _response.value = response.body()
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    _response.value = Response("Error: ${t.message}")
                }
            })
    }
}
