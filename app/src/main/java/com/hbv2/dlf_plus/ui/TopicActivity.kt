package com.hbv2.dlf_plus.ui

import android.opengl.Visibility
import android.os.Bundle

import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
import com.hbv2.dlf_plus.ui.messagelistfragment.adapter.MessageListAdapter
import com.hbv2.dlf_plus.ui.messagelistfragment.viewmodel.MessageListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.hbv2.dlf_plus.ui.topiccreatefragment.TopicService

import com.hbv2.dlf_plus.ui.topiccreatefragment.view.EditTopicFragment
import java.util.*


class TopicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopicBinding
    private lateinit var msgRecyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager

    private var adapter: MessageListAdapter? = null

    private val messageListViewModel: MessageListViewModel  by lazy {
        ViewModelProvider(this).get(MessageListViewModel::class.java)
    }

    private lateinit var topicService: TopicService
    private lateinit var topic: Topic

    private lateinit var currentUser: User



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)

        // nsfw
        currentUser = sessionManager.fetchAuthedUserDetails()?.user as User

        topicService = TopicService(this, sessionManager)
        binding = ActivityTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val _id = intent.getIntExtra("TOPIC_ID", -1) // ehv svona // fra danna?? passar.
        val _title = intent.getStringExtra("TOPIC_TITLE") // fra danna??
        val _desc = intent.getStringExtra("TOPIC_DESCRIPTION") // fra danna??

        // todo kannski trim
        // samt bara placeholder fyrir fetchid
        topic = Topic(
            id = _id,
            title = _title.toString(),
            description = _desc.toString()
        )

        messageListViewModel
            .getMessagesLiveData()
            .observe(this, Observer<List<Message>> { msg ->
                updateUI()
            })

        val thisTopicId = intent.getIntExtra("TOPIC_ID", 1)

        topicService.getTopicByid(thisTopicId)
        // buinn ad na i topic, setja title og desc.


        // todo ná i topic by id, athuga hvort user hafi buid thad til, tha gera thessa takka visible


        binding.editButton.setOnClickListener {
            val editTopic = EditTopicFragment.newInstance()
            editTopic.show(supportFragmentManager, "editTopic")
        }
        binding.deleteButton.setOnClickListener {

        }
        // edit thread endar

        msgRecyclerView = findViewById<RecyclerView>(R.id.recycler_gchat)
        msgRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun updateUI() {
        val _messages = messageListViewModel.getMessages()
        adapter = MessageListAdapter(_messages, currentUser.id)
        msgRecyclerView.adapter = adapter
    }

    private fun addMessageToViewModel(message: Message) {
        messageListViewModel.addMessage(message)
    }

    fun onTopicFetched(_topic: Topic) {
        Log.d("Topic activity", topic.toString())
        topic = _topic

        topic.messages.forEach { msg ->
            addMessageToViewModel(msg)
        }

        binding.title.text = topic.title;
        binding.description.text = topic.description;
        if (topic?.creator?.email == sessionManager.fetchAuthedUserDetails()?.user?.email) {
            binding.editButton.visibility = View.VISIBLE
            binding.deleteButton.visibility = View.VISIBLE
        }
    }



    fun errorFetching() {


    }
}