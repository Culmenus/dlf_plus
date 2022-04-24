package com.hbv2.dlf_plus.ui.forumcardlistfragment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.responses.ForumsResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ForumCardListViewModel"

class ForumCardListViewModel() : ViewModel() {

    val forums = ArrayList<Forum>()
    private val forumsLiveData = MutableLiveData<List<Forum>>()

    val backendApiClient = BackendApiClient()

    fun getForumsLiveData(): MutableLiveData<List<Forum>> {
        return forumsLiveData
    }

    fun addForum( forum: Forum ) {
        forums.add(forum)
        forumsLiveData.value = forums
    }

    fun resetForumList() {
        Log.d(TAG, "resettriggered")
        forums.clear()
        forumsLiveData.value = forums
    }

    fun loadForums(fetchCondition: Int, sessionManager: SessionManager) {
        when (fetchCondition) {
            0 -> fetchAllForums()
            1 -> fetchMyForums(sessionManager)
            else -> Log.d(TAG, "Invalid fetch condition")
        }
    }

    private fun fetchMyForums(sessionManager: SessionManager) {
        if(sessionManager.isUserStored()){
            val token = sessionManager.fetchAuthedUserDetails()?.token
            backendApiClient.getApi().getFavoriteForums(StringBuilder().append("Bearer ").append(token).toString())
                .enqueue(object : Callback<ArrayList<ForumsResponseItem>> {
                    override fun onFailure(
                        call: Call<ArrayList<ForumsResponseItem>>,
                        t: Throwable
                    ) {
                        Log.d(TAG, call.request().toString())
                    }

                    override fun onResponse(
                        call: Call<ArrayList<ForumsResponseItem>>,
                        response: Response<ArrayList<ForumsResponseItem>>
                    ) {
                        Log.d(TAG, "Request succeeded")
                        val forums = response.body()
                        if (response.isSuccessful && forums != null) {

                            forums.forEach { item ->
                                val tempForum = Forum(
                                    id = item.id,
                                    cover = R.drawable.pallas,
                                    courseId = item.courseId,
                                    name = item.name,
                                    description = item.description
                                )
                                addForum(tempForum)
                            }
                        } else {
                            //Error login
                            Log.d(TAG, "Failed to fetch")
                        }
                    }
                })
        } else {
            //User not logged in
        }
    }

    private fun fetchAllForums() {
        // do an async op to fetch forums
        backendApiClient.getApi()
            .getAllForums()
            .enqueue(object : Callback<ArrayList<ForumsResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<ForumsResponseItem>>,
                    t: Throwable
                ) {
                    Log.d(TAG, call.request().toString())
                }

                override fun onResponse(
                    call: Call<ArrayList<ForumsResponseItem>>,
                    response: Response<ArrayList<ForumsResponseItem>>
                ) {
                    Log.d(TAG, "Request succeeded")
                    val forums = response.body()
                    if (response.isSuccessful && forums != null) {

                        forums.forEach { item ->
                            val tempForum = Forum(
                                id = item.id,
                                cover = R.drawable.pallas,
                                courseId = item.courseId,
                                name = item.name,
                                description = item.description
                            )
                            addForum(tempForum)
                        }
                    } else {
                        //Error login
                        Log.d(TAG, "Failed to fetch")
                    }
                }
            })
    }
}