package com.hbv2.dlf_plus.networks.misc

object Constants {

    // Endpoints
    const val BASE_URL = "http://10.0.2.2:8080/"
    //user endpoints
    const val LOGIN_URL = "/login"


    //forum end points
    const val FORUMS_URL = "/api/forum"
    const val GET_FORUM_BY_ID_URL = "/api/forum/{id}"
    const val ADD_TO_FAVORITES_BY_ID_URL = "/api/favorite-forums/{id}"
    const val DELETE_FROM_FAVORITES_BY_ID_URL = "/api/delete-favorite-forums/{id}"

    //topic endpoints
    const val THREAD_URL = "/api/thread/{threadId}"
    const val CREATE_THREAD_BY_FORUM_ID = "/api/forum/{forumId}"


}