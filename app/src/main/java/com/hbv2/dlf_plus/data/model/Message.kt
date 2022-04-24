package com.hbv2.dlf_plus.data.model

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
    var createdAt: Date? = null,
) {
    constructor(msg: Message): this(msg.message, msg.isEdited, msg.sentBy.id, msg.sentBy.username, msg.createdAt)
}
