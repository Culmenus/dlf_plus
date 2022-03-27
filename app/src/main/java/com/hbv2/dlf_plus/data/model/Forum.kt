package com.hbv2.dlf_plus.data.model

// allt placeholder að fylgja tutorial

val FORUM_ID_EXTRA = "forumExtra"

//id daemid er placeholder
data class Forum (
    val id: Int,
    var cover: Int,
    var courseId: String = "",
    var name: String = "",
)