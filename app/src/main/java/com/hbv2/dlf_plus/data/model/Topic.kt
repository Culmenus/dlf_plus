package com.hbv2.dlf_plus.data.model

import com.hbv2.dlf_plus.networks.requestBody.ForumWithoutId
import com.hbv2.dlf_plus.networks.requestBody.TopicWithoutId
import java.util.*
import kotlin.collections.ArrayList

val TOPIC_ID_EXTRA = "threadExtra"

data class Topic(
    val creator: User? = null,
    var description: String = "",
    val id: Int = -1,
    var lastUpdated: Date? = null,
    var messages: ArrayList<Message> = ArrayList<Message>(),
    var title: String = "",
)

fun Topic.toTopicWithoutId() = TopicWithoutId(
    creator = creator,
    description = description,
    lastUpdated = lastUpdated,
    title = title,
)
