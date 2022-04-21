package com.hbv2.dlf_plus.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.ui.userprofile.view.UserProfileActivity
import com.hbv2.dlf_plus.databinding.ActivityMyForumsBinding
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.forumcardlistfragment.view.ForumCardListFragment
import com.hbv2.dlf_plus.ui.login.LoginActivity
import com.hbv2.dlf_plus.ui.topiclistfragment.view.TopicListFragment

private const val TAG = "MyForumActivity"
class MyForumsActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMyForumsBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyForumsBinding.inflate(layoutInflater)
        sessionManager = SessionManager(applicationContext)
        setContentView(binding.root)

        initDrawer()

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_forum_cards)

        if (currentFragment == null) {
            val fragment = ForumCardListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_forum_cards, fragment)
                .commit()
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "resumed")
        resetForumViewModel()
        loadForumViewModel()
    }

    fun resetForumViewModel() {
        val tm: ForumCardListFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_forum_cards) as ForumCardListFragment
        tm.resetForumList()
    }

    fun loadForumViewModel() {
        val tm: ForumCardListFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_forum_cards) as ForumCardListFragment
        tm.loadForumList()
    }

    // Drawer
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun initDrawer() {
        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miItem1 -> {
                    val intent = Intent(this@MyForumsActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.miItem2 -> {
                    val intent = Intent(this@MyForumsActivity, MyForumsActivity::class.java)
                    startActivity(intent)
                }
                R.id.miItem3 -> {
                    val intent = Intent(this@MyForumsActivity, UserProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.logout -> {
                    val intent = Intent(this@MyForumsActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    sessionManager.removeAuthedUser()
                    startActivity(intent);
                }
            }
            binding.drawerLayout.closeDrawer(binding.navView)
            true
        }
    }
}