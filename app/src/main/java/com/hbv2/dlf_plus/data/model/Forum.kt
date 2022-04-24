package com.hbv2.dlf_plus.data.model

import com.google.gson.annotations.SerializedName
import com.hbv2.dlf_plus.networks.requestBody.ForumWithoutId

val FORUM_ID_EXTRA = "forumExtra"

data class Forum (
    val id: Int,
    var cover: Int,
    var courseId: String = "",
    var name: String = "",
    var description: String = "",
    @SerializedName("threads")
    var topics: ArrayList<Topic> = ArrayList<Topic>(),
)

fun Forum.toForumWithoutId() = ForumWithoutId(
    courseId =  courseId,
    description = description,
    name = name,
    threads = topics
)