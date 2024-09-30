package com.faa.cuiportal.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faa.cuiportal.Model.User
import com.faa.cuiportal.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StaffRepository {

    private val _staffList = MutableLiveData<List<User>>()
    val staffList: LiveData<List<User>>
        get() = _staffList

    fun fetchStaffMembers() {
        RetrofitInstance.apiService.getStaffMembers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                Log.d("StaffRepository", "Response: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    _staffList.postValue(response.body())
                } else {
                    _staffList.postValue(emptyList())
                }
            }


            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _staffList.postValue(emptyList())
            }
        })
    }
}
