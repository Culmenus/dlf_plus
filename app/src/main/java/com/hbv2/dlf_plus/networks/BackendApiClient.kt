package com.hbv2.dlf_plus.networks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class BackendApiClient {
    private lateinit var backendApi: BackendApi

    fun getApi(): BackendApi {
        if(!::backendApi.isInitialized){
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            backendApi = retrofit.create(BackendApi::class.java)
        }
        return backendApi
    }
}