package com.faa.cuiportal.Model

data class ApiResponse(
    val message: String,
    val user_type: String? = null, // Ensure this matches the API response
    val user: User? = null          // Make this optional
)
