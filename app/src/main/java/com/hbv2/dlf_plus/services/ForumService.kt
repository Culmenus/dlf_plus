package com.hbv2.dlf_plus.services

import android.util.Log
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.responses.ForumsResponseItem
import com.hbv2.dlf_plus.ui.ForumActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ForumService(activity: ForumActivity, sessionManager: SessionManager) {
    private lateinit var activity: ForumActivity;
    private lateinit var sessionManager: SessionManager
    private var backendApiClient: BackendApiClient = BackendApiClient()

    init {
        if (activity != null) {
            this.activity = activity
            this.sessionManager = sessionManager
        }
    }
    fun isForumFavorite(forum: Forum): Boolean{
        if(!sessionManager.isUserStored()) return false;
        val tmpForum = ForumsResponseItem(forum.courseId, forum.description, forum.id, forum.name)
        return sessionManager.fetchAuthedUserDetails()?.user?.favoriteForums?.contains(tmpForum)!!;
    }

    fun addToFavs(forumId: Int){
        if(!sessionManager.isUserStored()) return;

        backendApiClient.getApi().addToFavorites("Bearer " + sessionManager.fetchAuthedUserDetails()?.token!!, forumId.toString()).enqueue(object :
            Callback<java.util.ArrayList<ForumsResponseItem>> {
            override fun onFailure(call: Call<ArrayList<ForumsResponseItem>>, t: Throwable) {
                activity.setToggle(false)
            }
            override fun onResponse(
                call: Call<ArrayList<ForumsResponseItem>>,
                response: Response<ArrayList<ForumsResponseItem>>
            ) {
                val forumResp: ArrayList<ForumsResponseItem>? = response.body()
                if(response.isSuccessful && forumResp != null){
                        sessionManager.updateFavorites(forumResp);
                        activity.setToggle(true)
                } else {
                    activity.setToggle(false)
                }
            }
        })
    }

    fun removeFromFavs(forumId: Int){
        if(!sessionManager.isUserStored()) return;

        backendApiClient.getApi().removeFromFavorites("Bearer " + sessionManager.fetchAuthedUserDetails()?.token!!, forumId.toString()).enqueue(object :
            Callback<java.util.ArrayList<ForumsResponseItem>> {
            override fun onFailure(call: Call<ArrayList<ForumsResponseItem>>, t: Throwable) {
                activity.setToggle(true)
            }
            override fun onResponse(
                call: Call<ArrayList<ForumsResponseItem>>,
                response: Response<ArrayList<ForumsResponseItem>>
            ) {
                val forumResp: ArrayList<ForumsResponseItem>? = response.body()
                if(response.isSuccessful && forumResp != null){
                    sessionManager.updateFavorites(forumResp);
                    activity.setToggle(false)


                } else {
                    activity.setToggle(true)
                }
            }
        })
    }

}