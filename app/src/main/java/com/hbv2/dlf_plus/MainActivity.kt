package com.hbv2.dlf_plus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ForumClickListener {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miItem1 -> Toast.makeText(applicationContext,
                    "Clicked item 1", Toast.LENGTH_SHORT).show()
                R.id.miItem2 -> Toast.makeText(applicationContext,
                    "Clicked item 2", Toast.LENGTH_SHORT).show()
                R.id.miItem3 -> Toast.makeText(applicationContext,
                    "Clicked item 3", Toast.LENGTH_SHORT).show()
            }
            true
        }

        populateForums()

        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(forumList, mainActivity)
        }

        /* dha dummy button yfir i forumActivity... skipta ut fyrir onclick a cards
        val dummyButton : Button = binding.forumDummyButton;
        dummyButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ForumActivity::class.java)
            startActivity(intent)

        }

         */

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


    override fun onClick(forum: Forum) {
        val intent = Intent(applicationContext, ForumActivity::class.java)
        intent.putExtra(FORUM_ID_EXTRA, forum.id)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}