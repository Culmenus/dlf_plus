package com.hbv2.dlf_plus.networks

import com.hbv2.dlf_plus.networks.Constants.FETCH_FORUMS_URL
import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.responses.AllForumsResponse
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import com.hbv2.dlf_plus.ui.main.view.MainActivity
import retrofit2.Call
import retrofit2.http.*


interface BackendApi {

    @POST(Constants.LOGIN_URL)
    fun login(@Body loginReq: LoginRequestBody): Call<LoginResponse>

    @GET(FETCH_FORUMS_URL)
    fun getAllForums(@Header("Authorization") token: String ) : Call<AllForumsResponse>
}