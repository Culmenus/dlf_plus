package com.hbv2.dlf_plus.ui.forum.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.ui.main.view.MyForumsActivity
import com.hbv2.dlf_plus.ui.userprofile.view.UserProfileActivity
import com.hbv2.dlf_plus.data.model.*
import com.hbv2.dlf_plus.ui.forum.adapter.TopicAdapter
import com.hbv2.dlf_plus.databinding.ActivityForumBinding
import com.hbv2.dlf_plus.ui.forum.TopicClickListener
import com.hbv2.dlf_plus.ui.main.view.MainActivity
import com.hbv2.dlf_plus.ui.topic.view.TopicActivity


class ForumActivity : AppCompatActivity(), TopicClickListener {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityForumBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForumBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initDrawer()

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.botItem1 -> {
                    // Respond to navigation item 1 click
                    true
                }
                R.id.botItem2 -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.botItem3 -> {
                    // Respond to navigation item 2 click
                    true
                }
                else -> false
            }
        }

        val forumID = intent.getIntExtra(FORUM_ID_EXTRA, -1)
        val forum = forumFromID(forumID)

        if(forum != null) {
            binding.cover.setImageResource(forum.cover)
            binding.name.text = forum.name
            binding.courseId.text = forum.courseId

            binding.recyclerViewTopics.apply {
                layoutManager = GridLayoutManager(applicationContext, 1)
                adapter = TopicAdapter(topicList,this@ForumActivity)
            }
        }
    }

    private fun forumFromID(forumID: Int): Forum? {
        for(forum in forumList) {
            if (forum.id == forumID)
                return forum
        }
        return null
    }

    // burger
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    fun initDrawer() {
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miItem1 -> { val intent = Intent(this@ForumActivity, MainActivity::class.java)
                    startActivity(intent) }
                R.id.miItem2 -> { val intent = Intent(this@ForumActivity, MyForumsActivity::class.java)
                    startActivity(intent) }
                R.id.miItem3 -> { val intent = Intent(this@ForumActivity, UserProfileActivity::class.java)
                    startActivity(intent) }
            }
            true
        }
    }

    override fun onClick(topic: Topic) {
        val intent = Intent(applicationContext, TopicActivity::class.java)
        intent.putExtra(TOPIC_ID_EXTRA, topic.id)
        startActivity(intent)
    }
}