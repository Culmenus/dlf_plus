package com.hbv2.dlf_plus.data.model

import java.util.*

var topicList = mutableListOf<Topic>()

val TOPIC_ID_EXTRA = "threadExtra"

data class Topic (
    var title: String,
    var description: String,
    val id: Int? = topicList.size
)
