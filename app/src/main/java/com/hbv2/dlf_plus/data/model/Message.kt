package com.hbv2.dlf_plus.data.model

import com.google.gson.annotations.SerializedName
import com.hbv2.dlf_plus.networks.requestBody.TopicWithoutId
import java.util.*

data class Message(
    val createdAt : Date,
    var message: String,
    var isEdited: Boolean,
    val sentBy : User,
    )

data class MessageDTO(
    var message: String,
    var isEdited: Boolean,
    var userID: Int,
    var username: String,
    var createdAt: Date = Date(),
)
