package com.hbv2.dlf_plus.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.User

import com.hbv2.dlf_plus.databinding.ActivityTopicBinding
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.forumcardlistfragment.viewmodel.ForumCardListViewModel
import com.hbv2.dlf_plus.ui.messagelistfragment.adapter.MessageListAdapter
import com.hbv2.dlf_plus.ui.messagelistfragment.viewmodel.MessageListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*


class TopicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopicBinding
    private lateinit var msgRecyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager

    private val messageListViewModel: MessageListViewModel  by lazy {
        ViewModelProvider(this).get(MessageListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        msgRecyclerView = findViewById<RecyclerView>(R.id.recycler_gchat)
        msgRecyclerView.layoutManager = LinearLayoutManager(this)

        val id = intent.getIntExtra("TOPIC_ID", -1) // ehv svona // fra danna?? passar.
        val title = intent.getStringExtra("TOPIC_TITLE") // fra danna??
        val desc = intent.getStringExtra("TOPIC_DESCRIPTION") // fra danna??

        /* mock
        val mockmsg = mutableListOf<Message>()
        val users = arrayOf<User>(
            User("jon@hi.is", username = "Jon", id = 1),
            User( "danni@hi.is", username = "Danni", id = 2)
        )

        //mock
        for(i in 1..20) {
            mockmsg.add(
                Message(
                    message = "msg ${i}",
                    createdAt = Date(),
                    isEdited = false,
                    sentBy = users[i % 2]
                )
            )
        }
         */

        val adapter = MessageListAdapter(mockmsg)
        msgRecyclerView.adapter = adapter
    }

    private fun addMessageToViewModel(message: Message) {
        messageListViewModel.addMessage(message)
    }

    private fun topicFromID(topicID: Int) {
        val backendApiClient = BackendApiClient()
        val token = sessionManager.fetchAuthedUserDetails()!!.token
        backendApiClient.getApi().getTopicById(
            StringBuilder().append("Bearer ").append(token).toString(),
            topicID.toString())
            .enqueue(object : Callback<Topic> {
                override fun onFailure(
                    call: Call<Topic>,
                    t: Throwable
                ) {
                    Log.d("Mainactivity", call.request().toString())
                }

                override fun onResponse(
                    call: Call<Topic>,
                    response: Response<Topic>
                ) {
                    Log.d("Mainactivity", "Request succeeded")
                    val topicRes = response.body()
                    if (response.isSuccessful && topicRes != null) {
                        Log.d("Mainactivity",topicRes.toString())
                        val _topic = Topic(
                            id = topicRes.id,
                            title = topicRes.title,
                            description = topicRes.description,
                            messages = topicRes.messages
                        )

                        addMessageToViewModel()
                    } else {
                        //Error login
                        Log.d("Mainactivity", "Failed to fetch")
                    }
                }
            })
    }
}