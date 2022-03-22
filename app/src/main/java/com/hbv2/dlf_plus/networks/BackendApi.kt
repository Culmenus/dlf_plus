package com.hbv2.dlf_plus.networks

import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApi {

    @POST(Constants.LOGIN_URL)
    fun login(@Body loginReq: LoginRequestBody): Call<LoginResponse>
}