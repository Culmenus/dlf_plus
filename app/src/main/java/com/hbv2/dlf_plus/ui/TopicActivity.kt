package com.hbv2.dlf_plus.ui

import android.opengl.Visibility
import android.os.Bundle
import android.se.omapi.Session
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.User

import com.hbv2.dlf_plus.databinding.ActivityTopicBinding
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.messagelistfragment.adapter.MessageListAdapter
import com.hbv2.dlf_plus.ui.topiccreatefragment.TopicService
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.CreateTopicFragment
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.EditTopicFragment
import com.hbv2.dlf_plus.ui.topiclistfragment.view.TopicListFragment
import com.hbv2.dlf_plus.ui.topiclistfragment.viewmodel.TopicListViewModel
import java.time.LocalDateTime
import java.util.*


class TopicActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityTopicBinding
    private lateinit var msgRecyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager
    private lateinit var topicService: TopicService
    private lateinit var topic: Topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)
        topicService = TopicService(this, sessionManager)
        binding = ActivityTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thisTopicId = intent.getIntExtra("TOPIC_ID", -1)
        Log.d("TopicActivity", "topic ID: $thisTopicId")

        topicService.getTopicByid(thisTopicId)

        // todo ná i topic by id, athuga hvort user hafi buid thad til, tha gera thessa takka visible

        binding.editButton.setOnClickListener {
            var editTopic = EditTopicFragment.newInstance()
            editTopic.show(supportFragmentManager, "editTopic")
        }
        binding.deleteButton.setOnClickListener {

        }

        msgRecyclerView = findViewById<RecyclerView>(R.id.recycler_gchat)
        msgRecyclerView.layoutManager = LinearLayoutManager(this)

        // mock
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

        val adapter = MessageListAdapter(mockmsg)
        msgRecyclerView.adapter = adapter
    }

    fun onTopicFetched(topic: Topic) {
        this.topic = topic
        Log.d("Topic activity", topic.toString())
        binding.title.text = topic.title;
        binding.description.text = topic.description;
        if (topic?.creator?.email == sessionManager.fetchAuthedUserDetails()?.user?.email) {
            binding.editButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.VISIBLE
            binding.toolbarCreatorOption.visibility = View.VISIBLE
        }
    }

    fun onTopicEdited(topic: Topic) {
        Log.d("Topic activity", topic.toString())
        binding.title.text = topic.title;
        binding.description.text = topic.description;
    }

    fun errorFetching(str: String) {

    }

    fun errorEditing(str: String) {

    }

    fun getSessionManager(): SessionManager {
        return this.sessionManager
    }

    fun getTopicService(): TopicService {
        return this.topicService
    }

    fun getTopicInstance(): Topic {
        return this.topic
    }
}