package com.hbv2.dlf_plus.services

import android.content.Context
import android.util.Log
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.toTopicWithoutId
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.requestBody.TopicWithoutId
import com.hbv2.dlf_plus.networks.responses.TopicResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ForumService(applicationContext: Context?) {
    private lateinit var sessionManager: SessionManager
    private lateinit var backendApiClient: BackendApiClient

    init {
        backendApiClient = BackendApiClient()
        if (applicationContext != null) {
            sessionManager = SessionManager(applicationContext)
        }
    }

    fun createTopic(topicCreated: Topic, forumId: String) {
        if(sessionManager.isUserStored()){
            val token = sessionManager.fetchAuthedUserDetails()?.token
            val topic: TopicWithoutId = topicCreated.toTopicWithoutId() //casting
            backendApiClient.getApi().createTopic(
                StringBuilder().append("Bearer ").append(token).toString(),
                forumId, topic)
                .enqueue(object : Callback<Topic> {
                    override fun onFailure(call: Call<Topic>, t: Throwable) {
                        Log.d("Mainactivity",call.request().toString())
                    }

                    override fun onResponse(
                        call: Call<Topic>,
                        response: Response<Topic>
                    ) {
                        Log.d("Create topic","Request succeeded")
                        val topicResponse: Topic? = response.body()
                        if(response.isSuccessful && topicResponse != null){
                            Log.d("Create topic",topicResponse.toString())
                        }else{
                            //Error login
                            Log.d("Create topic","Failed to fetch")
                        }
                    }
                })
        }else{
            //User not logged in do something..
        }
    }
}