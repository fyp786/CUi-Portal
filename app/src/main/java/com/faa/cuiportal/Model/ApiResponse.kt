package com.faa.cuiportal.Model

data class ApiResponse(
    val status: String,
    val message: String,
    val username: String? = null,
    val email: String? = null,
    val user_id: Int? = null,
    val user_type: String? = null,
    val user: User? = null,
    val data: Any? = null
)
