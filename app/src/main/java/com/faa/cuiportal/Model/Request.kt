package com.faa.cuiportal.Model

data class Request(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val roomNumber: String,
    val username: String,
    val status: String
)
