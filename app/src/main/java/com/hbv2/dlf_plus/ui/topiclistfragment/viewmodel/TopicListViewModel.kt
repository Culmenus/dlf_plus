package com.hbv2.dlf_plus.ui.topiclistfragment.viewmodel

import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.data.model.Topic

class TopicListViewModel : ViewModel() {
    //todo check kafla 11 fyrir betri geymslu
    val topics = mutableListOf<Topic>()

    init {
        // Gen data for now. Sbr. bók bls. 177
        for (i in 0 until 100) {
            val topic = Topic()
            topic.title = "Topic title#$i"
            topic.description = "Topic desc #$i"
            topics += topic
        }
    }
}