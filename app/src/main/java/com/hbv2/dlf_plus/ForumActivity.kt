package com.hbv2.dlf_plus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hbv2.dlf_plus.databinding.ActivityForumBinding

class ForumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val forumID = intent.getIntExtra(FORUM_ID_EXTRA, -1)
        val forum = forumFromID(forumID)

        /*
        val dummyBackButton: Button = findViewById(R.id.btnOpenMain)
        dummyBackButton.setOnClickListener {
            val i = Intent(this@ForumActivity, MainActivity::class.java)
            startActivity(i)
        }

        val dummyThreadButton : Button = findViewById(R.id.dummybtnOpenThread);
        dummyThreadButton.setOnClickListener {
            val intent = Intent(this@ForumActivity, ThreadActivity::class.java)
            startActivity(intent)
        }
         */

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
}