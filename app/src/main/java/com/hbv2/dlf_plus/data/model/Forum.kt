package com.hbv2.dlf_plus.data.model

// allt placeholder að fylgja tutorial

var forumList = mutableListOf<Forum>()

val FORUM_ID_EXTRA = "forumExtra"

data class Forum (
    var cover: Int,
    var courseId: String,
    var name: String,
    val id: Int? = forumList.size
)