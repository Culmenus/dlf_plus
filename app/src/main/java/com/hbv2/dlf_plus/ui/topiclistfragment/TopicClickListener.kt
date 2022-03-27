package com.hbv2.dlf_plus.ui.topiclistfragment

import com.hbv2.dlf_plus.data.model.Topic

interface TopicClickListener {
    fun onClick(topic: Topic)
}