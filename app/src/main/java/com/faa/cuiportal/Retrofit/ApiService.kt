package com.faa.cuiportal.Retrofit

import com.faa.cuiportal.Model.ApiResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("signup.php")
    fun signUp(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("user_type") userType: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("login.php") // Ensure this path is correct
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ApiResponse>
}
