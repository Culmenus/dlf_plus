package com.hbv2.dlf_plus.data.model

import com.google.gson.annotations.SerializedName
import com.hbv2.dlf_plus.networks.requestBody.ForumWithoutId

// allt placeholder að fylgja tutorial

val FORUM_ID_EXTRA = "forumExtra"

//id daemid er placeholder

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