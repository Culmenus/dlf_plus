package com.hbv2.dlf_plus.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity

import com.hbv2.dlf_plus.databinding.ActivityTopicBinding


class TopicActivity : AppCompatActivity() {
//    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityTopicBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTopicBinding.inflate(layoutInflater)

        val id = intent.getStringExtra("TOPIC_ID")?.toInt() // ehv svona
        val title = intent.getStringExtra("TOPIC_TITLE")
        val desc = intent.getStringExtra("TOPIC_DESCRIPTION")

        binding.title.text = title
        binding.description.text = desc

        setContentView(binding.root)
    }
}