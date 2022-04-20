package com.hbv2.dlf_plus.ui.topiccreatefragment

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.TopicActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class TopicService(activity: TopicActivity, sessionManager: SessionManager) {
    private lateinit var activity: TopicActivity
    private lateinit var sessionManager: SessionManager
    private var backendApiClient: BackendApiClient = BackendApiClient()

    init {
        if (activity != null) {
            this.activity = activity
            this.sessionManager = sessionManager
        }
    }

    fun getTopicByid(topicId: Int) {
        if(sessionManager.isUserStored()){
            val token = sessionManager.fetchAuthedUserDetails()?.token
            backendApiClient.getApi().getTopicById(StringBuilder().append("Bearer ").append(token).toString(),
                topicId.toString())
                .enqueue(object : Callback<Topic> {
                    override fun onFailure(call: Call<Topic>, t: Throwable) {
                        Log.d("Get topic by ID",call.request().toString())
                    }
                    override fun onResponse(
                        call: Call<Topic>,
                        response: Response<Topic>
                    ) {
                        Log.d("Get topic by ID","Request succeeded")
                        val topicRes: Topic? = response.body()
                        if(response.isSuccessful && topicRes != null){
                            // fragment.topicCreated(topicRes)
                            activity.onTopicFetched(topicRes)

                        } else {
                            //Error fetching
                            Log.d("Get topic by ID",response.toString())
                            // fragment.errorFetching("An error occurred")
                            activity.errorFetching()
                        }
                    }
                })
        } else {
            //User not logged in
            // Toast.makeText(context, "User must be logged in", Toast.LENGTH_LONG).show()
        }
    }
}