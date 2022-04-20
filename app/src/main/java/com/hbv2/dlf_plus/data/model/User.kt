package com.hbv2.dlf_plus.data.model

data class User(
    val email: String,
    val id: Int,
    val userRole: String = "ROLE_USER",
    val username: String
)