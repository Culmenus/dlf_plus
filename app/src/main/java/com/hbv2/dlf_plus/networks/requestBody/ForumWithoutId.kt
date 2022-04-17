package com.hbv2.dlf_plus.networks.requestBody

data class ForumWithoutId(
    val courseId: String,
    val description: String,
    val name: String,
    val threads: List<Any>
)