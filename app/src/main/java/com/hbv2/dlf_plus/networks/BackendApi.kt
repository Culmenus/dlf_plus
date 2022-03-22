package com.hbv2.dlf_plus.networks

import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApi {

    @POST("/login")
    fun login(@Body loginReq: LoginRequestBody): Call<LoginResponse>
}