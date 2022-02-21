package com.hbv2.dlf_plus.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.databinding.ActivityThreadBinding


class ThreadActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewBinding: ActivityThreadBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityThreadBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        initBoigah()

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
                R.id.miItem1 -> { val intent = Intent(this@ThreadActivity, MainActivity::class.java)
                    startActivity(intent) }
                R.id.miItem2 -> { val intent = Intent(this@ThreadActivity, MyForumsActivity::class.java)
                    startActivity(intent) }
                R.id.miItem3 -> { val intent = Intent(this@ThreadActivity, UserProfileActivity::class.java)
                    startActivity(intent) }
            }
            true
        }
    }
}