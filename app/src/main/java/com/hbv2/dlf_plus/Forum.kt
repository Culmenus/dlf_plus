package com.hbv2.dlf_plus

// allt placeholder að fylgja tutorial

var forumList = mutableListOf<Forum>()

class Forum (
    var cover: Int,
    var courseId: String,
    var name: String,
    val id: Int? = forumList.size
)