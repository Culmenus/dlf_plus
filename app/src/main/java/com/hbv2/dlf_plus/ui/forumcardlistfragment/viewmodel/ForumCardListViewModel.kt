package com.hbv2.dlf_plus.ui.forumcardlistfragment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Forum
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.responses.ForumsResponseItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForumCardListViewModel : ViewModel() {

    val forums = ArrayList<Forum>()
    private val forumsLiveData = MutableLiveData<List<Forum>>()

    val backendApiClient = BackendApiClient()
    // todo tengja þetta login
    // þetta er bara copy paste frá postman eins og er

    fun getForumsLiveData(): MutableLiveData<List<Forum>> {
        return forumsLiveData
    }

    fun addForum( forum: Forum ) {
        forums.add(forum)
        forumsLiveData.value = forums
    }

    private fun loadForums() {
        // do an async op to fetch forums
        backendApiClient.getApi()
            .getAllForums()
            .enqueue(object : Callback<ArrayList<ForumsResponseItem>> {
                override fun onFailure(
                    call: Call<ArrayList<ForumsResponseItem>>,
                    t: Throwable
                ) {
                    Log.d("Mainactivity", call.request().toString())
                }

                override fun onResponse(
                    call: Call<ArrayList<ForumsResponseItem>>,
                    response: Response<ArrayList<ForumsResponseItem>>
                ) {
                    Log.d("Mainactivity", "Request succeeded")
                    val allForums = response.body()
                    if (response.isSuccessful && allForums != null) {

                        allForums.forEach { item ->
                            val tempForum = Forum(
                                id = item.id,
                                cover = R.drawable.pallas,
                                courseId = item.courseId,
                                name = item.name,
                                description = item.description
                            )
                            addForum(tempForum)
                        }
                        //Log.d("Mainactivity",allForums.toString())
                    } else {
                        //Error login
                        Log.d("Mainactivity", "Failed to fetch")
                    }
                }
            })
    }

    init {
        loadForums()
    }

}