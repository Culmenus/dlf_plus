package com.hbv2.dlf_plus.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.data.model.*
import com.hbv2.dlf_plus.databinding.ActivityForumBinding
import com.hbv2.dlf_plus.ui.forumcardlistfragment.viewmodel.ForumCardListViewModel
import com.hbv2.dlf_plus.ui.topiccreatefragment.OnTopicCreated
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.CreateTopicFragment
import com.hbv2.dlf_plus.ui.topiclistfragment.view.TopicListFragment


class ForumActivity : AppCompatActivity(), OnTopicCreated {
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

        // create topic virkni
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//
        binding.createTopic.setOnClickListener {
            var createTopic = CreateTopicFragment.newInstance()
            createTopic.show(supportFragmentManager, "createTopic")
        }
        // create topic virkni endar
        //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//


        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
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

        val forumID = intent.getIntExtra("FORUM_ID_EXTRA", -1)
        val forum = forumFromID(forumID)

        if (forum != null) {
            binding.cover.setImageResource(forum.cover)
            binding.name.text = forum.name
            binding.courseId.text = forum.courseId
        }
    }


    private fun forumFromID(forumID: Int): Forum? {
        //TODO LAGA. Verðum að leysa þetta með fetch eða local db
        //placeholder, verður leyst betur
        val mockforums = mutableListOf<Forum>()
        // temp mock data
        val mforum1 = Forum(
            1,
            R.drawable.pallas,
            "Tol999",
            "Forritun",
        )
        mockforums += mforum1

        val mforum2 = Forum(
            2,
            R.drawable.pallasblue,
            "Stæ999",
            "Stærðfræði",
        )
        mockforums += mforum2
        val mforum3 = Forum(
            3,
            R.drawable.img,
            "Cov19",
            "Veikur",
        )
        mockforums += mforum3

        for (forum in mockforums) {
            if (forum.id == forumID)
                return forum
        }
        return null
    }

    override fun onTopicCreated(topic: Topic) {
        TODO("Not yet implemented")
        Toast.makeText(this, "yabba dabba doooo", Toast.LENGTH_LONG).show()
    }
}
