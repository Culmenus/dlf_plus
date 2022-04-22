package com.hbv2.dlf_plus.ui

import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.MessageDTO
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.User

import com.hbv2.dlf_plus.databinding.ActivityTopicBinding
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.networks.websocket.WSChatClient
import com.hbv2.dlf_plus.ui.messagelistfragment.adapter.MessageListAdapter
import com.hbv2.dlf_plus.ui.messagelistfragment.viewmodel.MessageListViewModel
import com.hbv2.dlf_plus.services.TopicService
import com.hbv2.dlf_plus.ui.topicdeletefragment.view.DeleteTopicFragment
import com.hbv2.dlf_plus.ui.topiceditfragment.view.EditTopicFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//

//


class TopicActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityTopicBinding
    private lateinit var msgRecyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager
    private lateinit var stompClient : WSChatClient
    private var adapter: MessageListAdapter? = null

    private val messageListViewModel: MessageListViewModel  by lazy {
        ViewModelProvider(this)[MessageListViewModel::class.java]
    }

    private lateinit var topicService: TopicService
    private lateinit var topic: Topic
    private lateinit var currentUser: User
    private lateinit var backendApiClient: BackendApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(applicationContext)
        backendApiClient = BackendApiClient()
        stompClient = WSChatClient()
        stompClient.connect()
        // nsfw
        val userDetails = sessionManager.fetchAuthedUserDetails()
        currentUser = userDetails?.user!!
        val token = userDetails.token

        topicService = TopicService(this, sessionManager)
        binding = ActivityTopicBinding.inflate(layoutInflater)
        val submit = binding.buttonGchatSend
        val text = binding.editGchatMessage
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val _id = intent.getIntExtra("TOPIC_ID", -1) // ehv svona // fra danna?? passar.
        val _title = intent.getStringExtra("TOPIC_TITLE") // fra danna??
        val _desc = intent.getStringExtra("TOPIC_DESCRIPTION") // fra danna??

        stompClient.subscribe(_id) {
            messageListViewModel.addMessage(it)
        }
        submit.setOnClickListener {
            val msg = MessageDTO(text.text.toString(), false, currentUser.id, currentUser.username)
            stompClient.sendMessage(_id, msg);
            backendApiClient.getApi().createMessageByThreadId(
                StringBuilder().append("Bearer ").append(token).toString(),
                _id.toString(),
                msg).enqueue(object : Callback<MessageDTO> {
                override fun onFailure(call: Call<MessageDTO>, t: Throwable) {
                    Log.d("Mainactivity",call.request().toString())
                }

                override fun onResponse(
                    call: Call<MessageDTO>,
                    response: Response<MessageDTO>
                ) {
                    Log.d("Mainactivity","Request succeeded")
                    val message = response.body()
                    if(response.isSuccessful && message != null){
                        Log.d("Mainactivity",message.toString())
                    }else{
                        //Error login
                        Log.d("Mainactivity","Failed to fetch")
                    }
                }
            })
            text.text.clear()
        }

        // todo kannski trim
        // samt bara placeholder fyrir fetchid
        topic = Topic(
            id = _id,
            title = _title.toString(),
            description = _desc.toString()
        )

        messageListViewModel
            .getMessagesLiveData()
            .observe(this, Observer<List<MessageDTO>> {
                updateUI()
                binding.recyclerGchat.scrollToPosition(it.size -1)
            })

        topicService.getTopicById(_id)

        msgRecyclerView = findViewById<RecyclerView>(R.id.recycler_gchat)
        msgRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun showCreatorOptions(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.topic_creator_options, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.edit_menu_button -> {
                    val editTopic = EditTopicFragment.newInstance()
                    editTopic.show(supportFragmentManager, "editTopic")
                }
                R.id.delete_menu_button -> {
                    val deleteTopic = DeleteTopicFragment.newInstance()
                    deleteTopic.show(supportFragmentManager, "deleteTopic")
                }
            }
            true
        }
    }

    private fun updateUI() {
        val messages = messageListViewModel.getMessages()
        adapter = MessageListAdapter(messages, currentUser.id)
        msgRecyclerView.adapter = adapter
    }

    private fun addMessageToViewModel(message: Message) {
        messageListViewModel.addMessage(message)
    }

    fun onTopicFetched(_topic: Topic) {
        Log.d("Topic activity", topic.toString())
        topic = _topic
        binding.topicCreatorName.text = topic.creator?.username
        topic.messages.forEach { msg ->
            addMessageToViewModel(msg)
        }

        binding.title.text = topic.title;
        binding.description.text = topic.description;
        if (topic?.creator?.email == sessionManager.fetchAuthedUserDetails()?.user?.email) {
            // binding.editButton.visibility = View.VISIBLE
            // binding.deleteButton.visibility = View.VISIBLE
            binding.showCreatorOptions.visibility = View.VISIBLE
            binding.showCreatorOptions.setOnClickListener {
                showCreatorOptions(it)
            }
        }
    }


    fun onTopicEdited(topic: Topic) {
        Log.d("Topic activity", topic.toString())
        binding.title.text = topic.title;
        binding.description.text = topic.description;
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun onTopicDeleted() {
        finish()
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

    override fun onDestroy() {
        super.onDestroy()
        stompClient.closeConnection()
    }
}