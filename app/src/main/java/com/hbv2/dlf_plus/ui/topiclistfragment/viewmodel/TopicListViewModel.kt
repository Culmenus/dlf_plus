package com.hbv2.dlf_plus.ui.topiclistfragment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.data.model.Topic

import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.responses.ForumsResponseItem
import org.jetbrains.annotations.NotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class TopicListViewModel() : ViewModel() {
    val topics = ArrayList<Topic>()
    private val topicsLiveData = MutableLiveData<List<Topic>>()


    // má kannski servica þetta í burtu því þetta er endurtekinn kóði
    val backendApiClient = BackendApiClient()
    // TODO get rid of this shit

    fun getTopicsLiveData(): MutableLiveData<List<Topic>> {
        return topicsLiveData
    }

    fun createTopic(topic: Topic ) {
        topics.add(topic)
        topicsLiveData.value = topics
    }

    val token = "token"
    private fun loadTopic(topicID: Int) {
        // do an async op to fetch forums

        backendApiClient.getApi()
            .getTopicById(
                StringBuilder().append("Bearer ").append(token).toString(),
                topicID.toString()
            )
            .enqueue(object : Callback<Topic> {
                override fun onFailure(
                    call: Call<Topic>,
                    t: Throwable
                ) {
                    Log.d("Mainactivity", call.request().toString())
                }

                override fun onResponse(
                    call: Call<Topic>,
                    response: Response<Topic>
                ) {
                    Log.d("Mainactivity", "Request succeeded")
                    val topicRes = response.body()
                    if (response.isSuccessful && topicRes != null) {
                        Log.d("Mainactivity",topicRes.toString())
                        val _topic = Topic(
                            id = topicRes.id,
                            title = topicRes.title,
                            description = topicRes.description
                        )
                        addTopic(_topic)
                    } else {
                        //Error login
                        Log.d("Mainactivity", "Failed to fetch")
                    }
                }
            })

    fun resetTopicList() {
        topics.clear()

    }
}}