package com.faa.cuiportal.Model

data class User(
    val userId: String,      // Ensure this matches API response property names
    val username: String,
    val email: String,
    val password: String,
    val userType: String
)
