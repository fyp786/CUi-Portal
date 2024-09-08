package com.faa.cuiportal.Model

data class ApiResponse(
    val username: String? = null,
    val message: String,
    val user_type: String? = null, // Ensure this matches the API response
    val user: User? = null,
    val data: Any? = null // or other fields based on your API response
// Make this optional
)
