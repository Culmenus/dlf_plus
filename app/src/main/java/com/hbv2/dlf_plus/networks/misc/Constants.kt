package com.hbv2.dlf_plus.networks.misc

object Constants {

    //WS url
    const val WS_URL = "ws://10.0.2.2:8080/thread/websocket"
    // Endpoints
    const val BASE_URL = "http://10.0.2.2:8080/"
    //user endpoints
    const val LOGIN_URL = "/login"


    //forum end points
    const val FORUMS_URL = "/api/forum"
    const val GET_FORUM_BY_ID_URL = "/api/forum/{id}"
    const val GET_FAVORITES = "/api/favorite-forums"
    const val ADD_TO_FAVORITES_BY_ID_URL = "/api/favorite-forums/{forumId}"
    const val DELETE_FROM_FAVORITES_BY_ID_URL = "/api/delete-favorite-forums/{forumId}"

    //topic endpoints
    const val THREAD_URL = "/api/thread/{threadId}"
    const val THREAD_PATCH_URL = "/api/thread/{threadId}/title/{newTitle}/desc/{newDescription}"
    const val CREATE_THREAD_BY_FORUM_ID = "/api/forum/{forumId}"

    //messages
    const val MESSAGE_URL = "/api/message/{messageId}"


}