package com.hbv2.dlf_plus.networks

import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.data.model.MessageDTO
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.networks.misc.Constants
import com.hbv2.dlf_plus.networks.requestBody.ForumWithoutId
import com.hbv2.dlf_plus.networks.requestBody.LoginRequestBody
import com.hbv2.dlf_plus.networks.requestBody.TopicWithoutId
import com.hbv2.dlf_plus.networks.requestBody.TopicWithoutMessages
import com.hbv2.dlf_plus.networks.responses.AllForumsResponse
import com.hbv2.dlf_plus.networks.responses.ForumsResponseItem
import com.hbv2.dlf_plus.networks.responses.LoginResponse
import retrofit2.Call
import retrofit2.http.*


import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApi {

    //-----------user------------- //logout virkni ætti bara að vera sharedPreferences.
    //update/delete user bætist ef við ætlum að utfæra það..
    @POST(Constants.LOGIN_URL)
    fun login(@Body loginReq: LoginRequestBody): Call<LoginResponse>


    //------------forums------------
    @GET(Constants.FORUMS_URL)
    fun getAllForums() : Call<ArrayList<ForumsResponseItem>>

    @GET(Constants.GET_FORUM_BY_ID_URL)
    fun getForumById(@Header("Authorization") token: String ,
                     @Path("id") id: String) : Call<Forum>

    @GET(Constants.GET_FAVORITES)
    fun getFavoriteForums(@Header("Authorization") token: String) : Call<ArrayList<ForumsResponseItem>>


    @POST(Constants.ADD_TO_FAVORITES_BY_ID_URL)
    fun addToFavorites(@Header("Authorization") token: String,
                       @Path("forumId") forumId : String) : Call<ArrayList<ForumsResponseItem>>

    @POST(Constants.DELETE_FROM_FAVORITES_BY_ID_URL) //untested, ætti að virka eins og add to favorites
    fun removeFromFavorites(@Header("Authorization") token: String,
                       @Path("forumId") forumId : String) : Call<ArrayList<ForumsResponseItem>>

    //-----------------Topics(threads)-------

    @GET(Constants.THREAD_URL)
    fun getTopicById(@Header("Authorization") token: String,
                      @Path("threadId") threadId : String) : Call<Topic>

    @POST(Constants.CREATE_THREAD_BY_FORUM_ID)
    fun createTopic(@Header("Authorization") token: String,
                     @Path("forumId") forumId : String, @Body topic: TopicWithoutId) : Call<Topic>

    @PATCH(Constants.THREAD_URL)
    fun updateTopicById(@Header("Authorization") token: String,
                        @Body topic: TopicWithoutMessages,
                        @Path("threadId") threadId: String) : Call<Topic>

    @DELETE(Constants.THREAD_URL)
    fun deleteTopicById(@Header("Authorization") token: String,
                        @Path("threadId") threadId: String) : Call<Boolean>


    //---------------Messages---------------------------------------------
    @POST(Constants.THREAD_URL)
    fun createMessageByThreadId(@Header("Authorization") token: String,
                                @Path("threadId") threadId: String,
                                @Body message: MessageDTO
    ) : Call<MessageDTO>

    @PATCH(Constants.MESSAGE_URL)
    fun updateMessageByID(@Header("Authorization") token: String,
                          @Path("messageId") messageId: String,
                          @Body message: MessageDTO
    )

    @DELETE(Constants.MESSAGE_URL)
    fun deleteMessageById(@Header("Authoization") token: String,
                          @Path("messageId") messageId: String)

}