package com.hbv2.dlf_plus.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.hbv2.dlf_plus.*
import com.hbv2.dlf_plus.data.model.*
import com.hbv2.dlf_plus.ui.userprofile.view.UserProfileActivity
import com.hbv2.dlf_plus.ui.forumcardlistfragment.adapter.ForumCardAdapter
import com.hbv2.dlf_plus.databinding.ActivityMainBinding
import com.hbv2.dlf_plus.networks.BackendApiClient
//import com.hbv2.dlf_plus.networks.SessionManager
import com.hbv2.dlf_plus.ui.forumcardlistfragment.ForumClickListener
import com.hbv2.dlf_plus.ui.forumcardlistfragment.view.ForumCardListFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import org.hildan.krossbow.stomp.*
import org.hildan.krossbow.stomp.conversions.kxserialization.*
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {

    // sama og að gera
    //      var toggle: ActionBarDrawerToggle? = null
    // nema að þetta þarf fult af null checks
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    //private lateinit var sessionManager: SessionManager
    private lateinit var backendApiClient: BackendApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initDrawer()
        Log.d("MainActivity","Hello world!!")
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_forum_cards)

        if (currentFragment == null) {
            val fragment = ForumCardListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_forum_cards, fragment)
                .commit()
        }
        var threadId = 1
        GlobalScope.launch {
            val url = "http://127.0.0.1:8080/thread/"
            val session = StompClient().connect(url).withJsonConversions()
            session.use { s ->
                //s.convertAndSend("/threa", Person("Bob", 42), Person.serializer())

                // overloads without explicit serializers exist, but should be avoided if you also target JavaScript
                val messages: Flow<MessageDTO> = s.subscribe("/thread/1/get") //MessageDTO.serializer()

                messages.collect { msg ->
                    println(msg)
                }
            }

        }

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
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.miItem2 -> {
                    val intent = Intent(this@MainActivity, MyForumsActivity::class.java)
                    startActivity(intent)
                }
                R.id.miItem3 -> {
                    val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.nav_drawer_menu, menu)
        return true
    }

}