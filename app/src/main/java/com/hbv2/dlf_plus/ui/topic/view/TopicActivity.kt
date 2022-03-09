package com.hbv2.dlf_plus.ui.topic.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.ui.main.view.MyForumsActivity
import com.hbv2.dlf_plus.ui.userprofile.view.UserProfileActivity
import com.hbv2.dlf_plus.data.model.TOPIC_ID_EXTRA
import com.hbv2.dlf_plus.data.model.Topic
import com.hbv2.dlf_plus.data.model.topicList
import com.hbv2.dlf_plus.databinding.ActivityTopicBinding
import com.hbv2.dlf_plus.ui.main.view.MainActivity


class TopicActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityTopicBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTopicBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initDrawer()

        val topicID = intent.getIntExtra(TOPIC_ID_EXTRA, -1)
        val topic = topicFromID(topicID)

        if(topic != null) {
            binding.title.text = topic.title
            binding.description.text = topic.description
        }

    }


    private fun topicFromID(topicID: Int): Topic? {
        for(topic in topicList) {
            if(topic.id == topicID)
                return topic
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
                R.id.miItem1 -> { val intent = Intent(this@TopicActivity, MainActivity::class.java)
                    startActivity(intent) }
                R.id.miItem2 -> { val intent = Intent(this@TopicActivity, MyForumsActivity::class.java)
                    startActivity(intent) }
                R.id.miItem3 -> { val intent = Intent(this@TopicActivity, UserProfileActivity::class.java)
                    startActivity(intent) }
            }
            true
        }
    }
}