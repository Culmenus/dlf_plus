package com.hbv2.dlf_plus.ui.login

import com.hbv2.dlf_plus.networks.responses.LoginResponse

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoginResponse? = null,
    val error: Int? = null
)