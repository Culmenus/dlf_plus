package com.hbv2.dlf_plus.ui.forum.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.data.model.*
import com.hbv2.dlf_plus.ui.topiclistfragment.adapter.TopicAdapter
import com.hbv2.dlf_plus.databinding.ActivityForumBinding
import com.hbv2.dlf_plus.ui.topiclistfragment.TopicClickListener
import com.hbv2.dlf_plus.ui.topic.view.TopicActivity
import com.hbv2.dlf_plus.ui.topiclistfragment.view.TopicListFragment


class ForumActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityForumBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_forum)

        // fix sbr bls 179.
        if (currentFragment == null) {
            val fragment = TopicListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_forum, fragment)
                .commit()
        }

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

/*
    override fun onClick(topic: Topic) {
        val intent = Intent(applicationContext, TopicActivity::class.java)
        intent.putExtra(TOPIC_ID_EXTRA, topic.id)
        startActivity(intent)
    }

 */

}