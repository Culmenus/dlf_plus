package com.hbv2.dlf_plus.ui

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.data.model.*
import com.hbv2.dlf_plus.databinding.ActivityForumBinding
import android.util.Log
import android.widget.CheckBox
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.hbv2.dlf_plus.networks.BackendApiClient
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.services.ForumService
import com.hbv2.dlf_plus.ui.topiccreatefragment.OnTopicCreated
import com.hbv2.dlf_plus.ui.topiccreatefragment.TopicService
import com.hbv2.dlf_plus.ui.topiccreatefragment.view.CreateTopicFragment
import com.hbv2.dlf_plus.ui.topiclistfragment.view.TopicListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForumActivity : AppCompatActivity(), OnTopicCreated {
    private lateinit var toggle: CheckBox
    private lateinit var binding: ActivityForumBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var forum: Forum
    private lateinit var user: User
    private lateinit var forumService: ForumService;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sessionManager = SessionManager(applicationContext)
        forumService = ForumService(this, sessionManager)
        // its a user.
        user = sessionManager.fetchAuthedUserDetails()?.user!!

        //fav togglebutton
        // https://developer.android.com/guide/topics/ui/controls/togglebutton
        toggle = findViewById(R.id.fav_toggle_button)



        forum = Forum(
            id = intent.getIntExtra("FORUM_ID_EXTRA", -1),
            cover = intent.getIntExtra("FORUM_COVER_EXTRA", -1),
            courseId = intent.getStringExtra("FORUM_COURSEID_EXTRA").toString(),
            name = intent.getStringExtra("FORUM_NAME_EXTRA").toString(),
            description = intent.getStringExtra("FORUM_DESC_EXTRA").toString()
        )

        toggle.setOnClickListener {
            toggle.isEnabled = false;
            if(!toggle.isChecked) {
                forumService.removeFromFavs(forumId = forum.id)
            }
            else {
                forumService.addToFavs(forumId = forum.id)
            }
        }

        toggle.isChecked = forumService.isForumFavorite(forum)
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


        binding.cover.setImageResource(forum.cover)
        binding.name.text = forum.name
        binding.courseId.text = forum.courseId
        binding.descriptionOfCourse.text = forum.description

    }

     override fun onResume() {
         super.onResume()
         resetTopicViewModel()
         forumFromID(forum.id)
     }

    private fun setForum(_forum: Forum) {
        forum = _forum
    }

    fun setToggle(value: Boolean) {
        toggle.isEnabled = true
        toggle.isChecked = value
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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

    fun resetTopicViewModel() {
        val frag: Fragment? = supportFragmentManager?.findFragmentById(R.id.fragment_container_forum)
        var tm: TopicListFragment? = null
        if (frag != null) {
            tm = frag as TopicListFragment
        }
        tm?.resetTopicList()
    }

    override fun onTopicCreated(topic: Topic) {
        //Toast.makeText(this, "yabba dabba doooo" + topic.toString(), Toast.LENGTH_LONG).show()
        // Færum okkur yfir á þetta Topic:
        val tm: TopicListFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_forum) as TopicListFragment
        tm.addTopicToListView(topic)
        val intent = Intent(this@ForumActivity, TopicActivity()::class.java)
        intent.putExtra("TOPIC_ID", topic.id)
        intent.putExtra("TOPIC_TITLE", topic.title)
        intent.putExtra("TOPIC_DESCRIPTION", topic.description)

        startActivity(intent)
    }
}
