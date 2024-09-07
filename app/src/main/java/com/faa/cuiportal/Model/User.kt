package com.faa.cuiportal.Model

data class User(
    val username: String,
    val email: String,
    val password: String,
    val userType: String
)

// Response.kt
data class Response(val message: String)