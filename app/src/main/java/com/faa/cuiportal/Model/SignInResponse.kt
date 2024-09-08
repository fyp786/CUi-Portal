package com.faa.cuiportal.Model

data class SignInResponse(
    val user_type: String? = null, // Use user_type instead of userType to match what you're using
    val username: String? = null,
    val user_id: String? = null
)
