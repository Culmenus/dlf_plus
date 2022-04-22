package com.hbv2.dlf_plus.ui.topiclistfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.data.model.Topic

import com.hbv2.dlf_plus.networks.BackendApiClient

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

    fun resetTopicList() {
        topics.clear()
        topicsLiveData.value = topics
    }
}