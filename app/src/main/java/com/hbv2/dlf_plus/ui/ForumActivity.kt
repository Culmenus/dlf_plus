package com.hbv2.dlf_plus.ui

import android.content.Intent
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.data.model.*
import com.hbv2.dlf_plus.databinding.ActivityForumBinding
import android.util.Log
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.topiccreatefragment.OnTopicCreated
import com.hbv2.dlf_plus.ui.topiccreatefragment.OnTopicEdited
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.CreateTopicFragment
import com.hbv2.dlf_plus.ui.topiclistfragment.view.TopicListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForumActivity : AppCompatActivity(), OnTopicCreated, OnTopicEdited {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityForumBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var forum: Forum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(applicationContext)

        forum = Forum(
            id = intent.getIntExtra("FORUM_ID_EXTRA", -1),
            cover = intent.getIntExtra("FORUM_COVER_EXTRA", -1),
            courseId = intent.getStringExtra("FORUM_COURSEID_EXTRA").toString(),
            name = intent.getStringExtra("FORUM_NAME_EXTRA").toString(),
            description = intent.getStringExtra("FORUM_DESC_EXTRA").toString()
        )

        // fix
        val forumID = intent.getIntExtra("FORUM_ID_EXTRA", -1)
        forumFromID(forumID)

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
        binding.createTopic.setOnClickListener {
            var createTopic = CreateTopicFragment.newInstance()
            createTopic.show(supportFragmentManager, "createTopic")
        }

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

        if (forum != null) {
            binding.cover.setImageResource(forum.cover)
            binding.name.text = forum.name
            binding.courseId.text = forum.courseId
        }
    }

    private fun setForum(_forum: Forum) {
        forum = _forum
    }

    private fun forumFromID(forumID: Int) {
        //TODO LAGA. Verðum að leysa þetta með fetch eða local db
        // todo refactor ? ugh endurtekning
        val backendApiClient = BackendApiClient()
        val token = sessionManager.fetchAuthedUserDetails()!!.token
        backendApiClient.getApi().getForumById(
            StringBuilder().append("Bearer ").append(token).toString(),
            forumID.toString())
            .enqueue(object : Callback<Forum> {
                override fun onFailure(call: Call<Forum>, t: Throwable) {
                    Log.d("Mainactivity",call.request().toString())
                }

                override fun onResponse(
                    call: Call<Forum>,
                    response: Response<Forum>
                ) {
                    Log.d("Mainactivity","Request succeeded")
                    val forumRes = response.body()
                    if(response.isSuccessful && forumRes != null){
                        Log.d("Mainactivity",forumRes.toString())
                        val currentForum = Forum(
                            id = forumRes.id,
                            cover = R.drawable.pallas,
                            courseId = forumRes.courseId,
                            name = forumRes.name,
                            description = forumRes.description,
                            topics = forumRes.topics
                        )
                        setForum(currentForum)

                        currentForum.topics.forEach { topic ->
                            val tempTopic = Topic(
                                id = topic.id,
                                title = topic.title,
                                description = topic.description
                            )
                            addTopicToViewModel(tempTopic)
                        }
                        Log.d("forum topics", forum.toString())

                    }else{
                        //Error login
                        Log.d("Mainactivity","Failed to fetch")
                    }
                }
            })
    }

    fun addTopicToViewModel(topic: Topic) {
        val tm: TopicListFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_forum) as TopicListFragment
        tm.addTopicToListView(topic)
    }

    override fun onTopicCreated(topic: Topic) {
        //Toast.makeText(this, "yabba dabba doooo" + topic.toString(), Toast.LENGTH_LONG).show()
        // Færum okkur yfir á þetta Topic:
        val intent = Intent(this@ForumActivity, TopicActivity::class.java)
        intent.putExtra("TOPIC_ID", topic.id)
        intent.putExtra("TOPIC_TITLE", topic.title)
        intent.putExtra("TOPIC_DESCRIPTION", topic.description)
        val tm: TopicListFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_forum) as TopicListFragment
        tm.addTopicToListView(topic)
        startActivity(intent)
    }

    // todo stórlega breyta þessu og jafnvel beila á þetta fall...
    override fun onTopicEdited(topic: Topic) {
        Toast.makeText(this, topic.toString(), Toast.LENGTH_LONG).show()

    }
}
