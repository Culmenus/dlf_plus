package com.hbv2.dlf_plus.networks

import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface backendApi {

    @POST("/login")
    suspend fun login(@Body loginreq: LoginRequestBody): Response<LoginResponse>
}