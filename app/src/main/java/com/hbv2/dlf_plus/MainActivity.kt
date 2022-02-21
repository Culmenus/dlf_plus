package com.hbv2.dlf_plus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.hbv2.dlf_plus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    lateinit var toggle: ActionBarDrawerToggle





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)



        toggle = ActionBarDrawerToggle(this, viewBinding.drawerLayout, R.string.open, R.string.close)
        viewBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewBinding.navView.setNavigationItemSelectedListener {
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


        viewBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = CardAdapter(forumList)
        }

        // dha dummy button yfir i forumActivity... skipta ut fyrir onclick a cards
        // val dummyButton : Button = viewBinding.forumDummyButton;
        // dummyButton.setOnClickListener {
        //     val intent = Intent(this@MainActivity, ForumActivity::class.java)
        //     startActivity(intent)
//
        // }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
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
}