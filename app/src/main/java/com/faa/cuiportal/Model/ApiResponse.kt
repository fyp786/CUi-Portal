package com.faa.cuiportal.Model

data class ApiResponse(
    val username: String? = null,
    val message: String,
    val user_type: String? = null,
    val user: User? = null,
    val data: Any? = null
)
