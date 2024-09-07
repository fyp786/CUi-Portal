package com.faa.cuiportal.Repository

import com.faa.cuiportal.Retrofit.ApiService

class UserRepository(private val apiService: ApiService) {

    fun signUp(username: String, email: String, password: String, userType: String) =
        apiService.signUp(username, email, password, userType)

    fun login(email: String, password: String) =
        apiService.login(email, password)
}
