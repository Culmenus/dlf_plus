package com.hbv2.dlf_plus.networks.requestBody

import com.hbv2.dlf_plus.data.model.User
import com.hbv2.dlf_plus.networks.requestBody.ForumWithoutId
import com.hbv2.dlf_plus.networks.requestBody.TopicWithoutId
import java.util.*
import kotlin.collections.ArrayList

data class TopicWithoutMessages(
    val creator: User? = null,
    var description: String = "",
    val id: Int = -1,
    var lastUpdated: Date? = null,
    var title: String = "",
)
