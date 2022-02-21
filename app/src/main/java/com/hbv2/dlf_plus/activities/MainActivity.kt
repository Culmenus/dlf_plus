package com.hbv2.dlf_plus.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.CardAdapter
import com.hbv2.dlf_plus.Forum
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.databinding.ActivityMainBinding
import com.hbv2.dlf_plus.forumList

class MainActivity : AppCompatActivity() {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        initBoigah()

        populateForums()


        viewBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(forumList)
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
        toggle = ActionBarDrawerToggle(this, viewBinding.drawerLayout,
            R.string.open,
            R.string.close
        )
        viewBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewBinding.navView.setNavigationItemSelectedListener {
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
            R.drawable.pallas,
            "Stæ999",
            "Stærðfræði",
        )
        forumList.add(forum2)
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.nav_drawer_menu, menu)
        return true
    }*/


}