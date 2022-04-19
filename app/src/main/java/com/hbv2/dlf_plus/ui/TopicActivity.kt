package com.hbv2.dlf_plus.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.data.model.Message
import com.hbv2.dlf_plus.data.model.User

import com.hbv2.dlf_plus.databinding.ActivityTopicBinding
import com.hbv2.dlf_plus.ui.messagelistfragment.adapter.MessageListAdapter
import java.time.LocalDateTime
import java.util.*


class TopicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopicBinding
    private lateinit var msgRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

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