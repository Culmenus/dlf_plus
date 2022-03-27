package com.hbv2.dlf_plus.networks.responses

data class AllForumsResponseItem(
    val courseId: String,
    val description: String,
    val id: Int,
    val name: String,
    val threads: List<Any>
)