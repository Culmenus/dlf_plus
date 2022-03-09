package com.hbv2.dlf_plus.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ForumClickListener {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initBoigah()

        populateForums()


        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(forumList, mainActivity)
        }
    }


    // boigah
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)

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