package com.hbv2.dlf_plus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.adapters.CardAdapter
import com.hbv2.dlf_plus.databinding.ActivityMainBinding
import com.hbv2.dlf_plus.interfaces.ForumClickListener

class MainActivity : AppCompatActivity(), ForumClickListener {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initDrawer()

        populateForums()


        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(forumList, mainActivity)
        }
    }


    // Drawer
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
                R.id.miItem1 -> { val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent) }
                R.id.miItem2 -> { val intent = Intent(this@MainActivity, MyForumsActivity::class.java)
                    startActivity(intent) }
                R.id.miItem3 -> { val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
                    startActivity(intent) }
            }
            true
        }
    }


    private fun populateForums() {

        val thread1 = Topic(
            "gaur er eg eini",
            "eg fer ad grata",
        )
        topicList.add(thread1)

        val thread2 = Topic(
            "thessi structur er throt",
            "eg fer ad grata meira",
        )
        topicList.add(thread2)


        val forum1 = Forum(
            R.drawable.pallas,
            "Tol999",
            "Forritun",
        )
        forumList.add(forum1)

        val forum2 = Forum(
            R.drawable.pallasblue,
            "Stæ999",
            "Stærðfræði",
        )
        forumList.add(forum2)
        val forum3 = Forum(
            R.drawable.img,
            "Cov19",
            "Veikur",
        )
        forumList.add(forum3)
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.nav_drawer_menu, menu)
        return true
    }*/

    override fun onClick(forum: Forum) {
        val intent = Intent(applicationContext, ForumActivity::class.java)
        intent.putExtra(FORUM_ID_EXTRA, forum.id)
        startActivity(intent)
    }
}