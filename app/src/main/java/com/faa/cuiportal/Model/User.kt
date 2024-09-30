package com.faa.cuiportal.Model

data class User(
    val id: Int,
    val userId: Int,
    val username: String,
    val email: String,
    val password: String,
    val userType: String
)
