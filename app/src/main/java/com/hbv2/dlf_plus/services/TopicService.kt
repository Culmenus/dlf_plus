package com.hbv2.dlf_plus.ui.topiccreatefragment


import android.util.Log
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.TopicActivity
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.DeleteTopicFragment
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.EditTopicFragment
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
                            activity.errorFetching("An error occurred")
                        }
                    }
                })
        } else {
            //User not logged in
        }
    }

    fun editTopic(topicEdited: Topic, fragment: EditTopicFragment) {
        if (sessionManager.isUserStored()) {
            topicEdited.lastUpdated = null
            val token = sessionManager.fetchAuthedUserDetails()?.token
            backendApiClient.getApi().updateTopicById(
                StringBuilder().append("Bearer ").append(token).toString(),
                topicEdited.title, topicEdited.description, topicEdited.id.toString()
            )
                .enqueue(object : Callback<Topic> {
                    override fun onFailure(call: Call<Topic>, t: Throwable) {
                        Log.d("Edit topic", call.request().toString())
                    }

                    override fun onResponse(
                        call: Call<Topic>,
                        response: Response<Topic>
                    ) {
                        Log.d("Edit topic", "Request succeeded")
                        val topicRes: Topic? = response.body()
                        if (response.isSuccessful && topicRes != null) {
                            fragment.onTopicEdited(topicRes)
                        } else {
                            //Error fetching
                            Log.d("Edit topic", response.toString())
                            fragment.errorEditing("An error occurred")
                        }
                    }
                })
        } else {
            //User not logged in
        }
    }

    fun deleteTopic(topic: Topic, fragment: DeleteTopicFragment) {
        if (sessionManager.isUserStored()) {
            val token = sessionManager.fetchAuthedUserDetails()?.token
            backendApiClient.getApi().deleteTopicById(
                StringBuilder().append("Bearer ").append(token).toString(),
                topic.id.toString()
            )
                .enqueue(object : Callback<Boolean> {
                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                        Log.d("Edit topic", call.request().toString())
                    }

                    override fun onResponse(
                        call: Call<Boolean>,
                        response: Response<Boolean>
                    ) {
                        Log.d("Edit topic", "Request succeeded")
                        val topicRes: Boolean? = response.body()
                        if (response.isSuccessful && topicRes == true) {
                            fragment.onTopicDeleted()
                        } else {
                            //Error fetching
                            Log.d("Edit topic", response.toString())
                            fragment.errorDeleting("An error occurred")
                        }
                    }
                })
        } else {
            //User not logged in
        }
    }
}