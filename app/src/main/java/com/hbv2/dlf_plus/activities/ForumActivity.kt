package com.hbv2.dlf_plus.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.databinding.ActivityForumBinding


class ForumActivity : AppCompatActivity(), TopicClickListener {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityForumBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForumBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initBoigah()

        val forumID = intent.getIntExtra(FORUM_ID_EXTRA, -1)
        val forum = forumFromID(forumID)
        /*
        val dummyBackButton: Button = findViewById(R.id.btnOpenMain)
        dummyBackButton.setOnClickListener {
            val i = Intent(this@ForumActivity, MainActivity::class.java)
            startActivity(i)
        }

        val dummyThreadButton : Button = findViewById(R.id.dummybtnOpenThread)
        dummyThreadButton.setOnClickListener {
            val intent = Intent(this@ForumActivity, ThreadActivity::class.java)
            startActivity(intent)
        }*/

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

    // boigah
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun forumFromID(forumID: Int): Forum? {
        for(forum in forumList) {
            if (forum.id == forumID)
                return forum
        }
        return null
    }

    fun initBoigah() {
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