package com.hbv2.dlf_plus.networks.requestBody

import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.User
import java.util.*
import kotlin.collections.ArrayList

data class TopicWithoutId(
    val creator: User? = null,
    var description: String = "",
    var lastUpdated: Date? = null,
    var messages: ArrayList<Message> = ArrayList<Message>(),
    var title: String = "",
)
