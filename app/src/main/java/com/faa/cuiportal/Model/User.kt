package com.faa.cuiportal.Model

data class User(
    val userId: Int,
    val username: String,
    val email: String,
    val password: String,
    val userType: String
)
