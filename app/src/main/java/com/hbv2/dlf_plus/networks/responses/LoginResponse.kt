package com.hbv2.dlf_plus.networks.responses

import com.hbv2.dlf_plus.data.model.User

data class LoginResponse(
    val token: String,
    val user: User
)