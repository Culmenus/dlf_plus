package com.hbv2.dlf_plus.ui

import android.opengl.Visibility
import android.os.Bundle
import android.se.omapi.Session
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
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.CreateTopicFragment
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.EditTopicFragment
import java.time.LocalDateTime
import java.util.*


class TopicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopicBinding
    private lateinit var msgRecyclerView: RecyclerView
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)
        binding = ActivityTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // mock, notum getTopicById hér
        val topic = Topic(creator = sessionManager.fetchAuthedUserDetails()?.user, title= "yes po sir", description = "ljoti andarunginn",id = 1)

        // buinn ad na i topic, setja title og desc.
        //binding.title.text = topic.title;
        // binding.description.text = topic.description;

        // edit thread byrjar
        // todo ná i topic by id, athuga hvort user hafi buid thad til, tha gera thessa takka visible
        // if (topic sem fylgir nuverandi topicactivity (get by id)
        //      er created af nuverandi user (sessionManager.fetchAuthedUserDetails().user))
        binding.editButton.visibility = View.VISIBLE
        binding.deleteButton.visibility = View.VISIBLE


        binding.editButton.setOnClickListener {
            var editTopic = EditTopicFragment.newInstance()
            editTopic.show(supportFragmentManager, "editTopic")
        }
        binding.deleteButton.setOnClickListener {

        }
        // edit thread endar

        msgRecyclerView = findViewById<RecyclerView>(R.id.recycler_gchat)
        msgRecyclerView.layoutManager = LinearLayoutManager(this)

        val id = intent.getIntExtra("TOPIC_ID", -1) // ehv svona // fra danna?? passar.
        val title = intent.getStringExtra("TOPIC_TITLE") // fra danna??
        val desc = intent.getStringExtra("TOPIC_DESCRIPTION") // fra danna??

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
}