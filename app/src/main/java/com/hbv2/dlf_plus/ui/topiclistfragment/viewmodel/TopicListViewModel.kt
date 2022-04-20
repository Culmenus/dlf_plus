package com.hbv2.dlf_plus.ui.topiclistfragment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.data.model.Topic


class TopicListViewModel() : ViewModel() {
    val topics = ArrayList<Topic>()
    private val topicsLiveData = MutableLiveData<List<Topic>>()


    fun getTopicsLiveData(): MutableLiveData<List<Topic>> {
        return topicsLiveData
    }

    fun createTopic(topic: Topic ) {
        topics.add(topic)
        topicsLiveData.value = topics
    }

    fun resetTopicList() {
        topics.clear()
    }
}