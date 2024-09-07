package com.faa.cuiportal.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faa.cuiportal.Model.Response
import com.faa.cuiportal.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse

// SignInViewModel.kt
class SignInViewModel : ViewModel() {

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> get() = _response

    fun login(email: String, password: String) {
        RetrofitInstance.apiService.login(email, password)
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
