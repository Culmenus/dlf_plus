package com.hbv2.dlf_plus.ui.topiccreatefragment

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.toTopicWithoutId
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.requestBody.TopicWithoutId
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.EditTopicFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class EditTopicService(applicationContext: Context?, editTopicFragment: EditTopicFragment) {
    private lateinit var context: Context
    private lateinit var sessionManager: SessionManager
    private var backendApiClient: BackendApiClient = BackendApiClient()
    private var fragment: EditTopicFragment

    init {
        if (applicationContext != null) {
            context = applicationContext
            sessionManager = SessionManager(applicationContext)
        }
        fragment = editTopicFragment
    }

     fun editTopic(topicEdited: Topic, forumId: String) {
        if(sessionManager.isUserStored()){
            val token = sessionManager.fetchAuthedUserDetails()?.token
            // val topic: TopicWithoutId = topicEdited.toTopicWithoutId()
            backendApiClient.getApi().updateTopicById(
                StringBuilder().append("Bearer ").append(token).toString(),
                topicEdited, topicEdited.id.toString())
                .enqueue(object : Callback<Topic> {
                    override fun onFailure(call: Call<Topic>, t: Throwable) {
                        Log.d("Create topic",call.request().toString())
                        Toast.makeText(context, call.request().toString(), Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(
                        call: Call<Topic>,
                        response: Response<Topic>
                    ) {
                        Log.d("Create topic","Request succeeded")
                        val topicRes: Topic? = response.body()
                        if(response.isSuccessful && topicRes != null){
                            fragment.topicEdited(topicRes)
                        } else {
                            //Error fetching
                            Log.d("Create topic",response.toString())
                            fragment.errorFetching("An error occurred")
                        }
                    }
                })
        } else {
            //User not logged in
            Toast.makeText(context, "User must be logged in", Toast.LENGTH_LONG).show()
        }
    }
}