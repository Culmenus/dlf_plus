package com.hbv2.dlf_plus.ui.forum

import com.hbv2.dlf_plus.data.model.Topic

interface TopicClickListener {
    fun onClick(topic: Topic)
}