package com.hbv2.dlf_plus.data.model

data class User(
    val email: String,
    val favoriteForums: List<Forum>,
    val id: Int,
    val userRole: String,
    val username: String
)