package com.hbv2.dlf_plus.ui.topiclistfragment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.responses.ForumsResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicListViewModel : ViewModel() {

    // má alveg fegra þetta en þetta var lowest refactor work.
    val topics = ArrayList<Topic>()
    private val topicsLiveData = MutableLiveData<List<Topic>>()

    // má kannski servica þetta í burtu því þetta er endurtekinn kóði
    val backendApiClient = BackendApiClient()

    val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOjEsImlzcyI6InRoZUJveXMiLCJpYXQiOjE2NTAzNzczNTAsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdfQ.eC1OfeJ3gzkJ7PKGRyi5y2N5Q1FFdnLCxrmdwH7-yN3a3kZhMqq6KQegASktLa_thszoIj6hiOSsKoPrLvLB0A"

    fun getTopicsLiveData(): MutableLiveData<List<Topic>> {
        return topicsLiveData
    }

    fun addTopic( topic: Topic ) {
        topics.add(topic)
        topicsLiveData.value =topics
    }

    private fun loadTopics() {
        // do an async op to fetch forums
        backendApiClient.getApi()
            .getAllForums()
            .enqueue(object : Callback<ArrayList<ForumsResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<ForumsResponseItem>>,
                    t: Throwable
                ) {
                    Log.d("Mainactivity", call.request().toString())
                }

                override fun onResponse(
                    call: Call<ArrayList<ForumsResponseItem>>,
                    response: Response<ArrayList<ForumsResponseItem>>
                ) {
                    Log.d("Mainactivity", "Request succeeded")
                    val allForums = response.body()
                    if (response.isSuccessful && allForums != null) {

                        allTopics.forEach { item ->
                            val temp = Topic(
                                id = item.id,
                                title = item.
                            )
                            addTopic(temp)
                        }
                        //Log.d("Mainactivity",allForums.toString())
                    } else {
                        //Error login
                        Log.d("Mainactivity", "Failed to fetch")
                    }
                }
            })
}