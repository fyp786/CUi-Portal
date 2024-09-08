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
    @POST("login.php")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ApiResponse>
    @FormUrlEncoded
    @POST("complaints.php")
    fun newRequest(
        @Field("action") action: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("location") location: String,
        @Field("room_number") roomNumber: String,
        @Field("username") username: String

    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("complaints.php")
    fun pauseRequest(
        @Field("action") action: String,
        @Field("id") id: Int
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("complaints.php")
    fun resolveRequest(
        @Field("action") action: String,
        @Field("id") id: Int
    ): Call<ApiResponse>
}
