package com.hbv2.dlf_plus.data.model

import java.util.*

data class Message(
    val createdAt : Date,
    var message: String,
    var isEdited: Boolean,
    val sentBy : User,
    )
