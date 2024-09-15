package com.faa.cuiportal.Repository

import com.faa.cuiportal.Model.Request
import com.faa.cuiportal.Retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestRepository(private val apiService: ApiService) {

    fun getNewRequests(onResult: (List<Request>) -> Unit, onError: (String) -> Unit) {
        apiService.getNewRequests().enqueue(object : Callback<List<Request>> {
            override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) } ?: onError("Empty response body")
                } else {
                    onError("Error fetching new requests")
                }
            }

            override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                onError(t.message ?: "Unknown error")
            }
        })
    }

}
