package com.hbv2.dlf_plus.ui.userprofile.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.hbv2.dlf_plus.R
import com.hbv2.dlf_plus.databinding.ActivityUserProfileBinding
import com.hbv2.dlf_plus.networks.misc.SessionManager
import com.hbv2.dlf_plus.ui.MainActivity
import com.hbv2.dlf_plus.ui.MyForumsActivity
import com.hbv2.dlf_plus.ui.login.LoginActivity


class UserProfileActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        sessionManager = SessionManager(applicationContext)
        val user = sessionManager.fetchAuthedUserDetails()?.user!!
        val username = binding.username;
        val email = binding.email;
        val letter = binding.letterToCenter;
        setContentView(binding.root)
        username.text = user.username;
        email.text = user.email;
        letter.text = user.username[0].toString();
        binding.logoutBtn.setOnClickListener {
            val intent = Intent(this@UserProfileActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            sessionManager.removeAuthedUser()
            startActivity(intent);
        }

        initDrawer()

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
        val headerView: View = binding.navView.getHeaderView(0)
        val textView : TextView = headerView.findViewById(R.id.user_greeting)
        if(sessionManager.isUserStored()){
            textView.text = sessionManager.fetchAuthedUserDetails()?.user?.username
        }
            toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miItem1 -> { val intent = Intent(this@UserProfileActivity, MainActivity::class.java)
                    startActivity(intent) }
                R.id.miItem2 -> { val intent = Intent(this@UserProfileActivity, MyForumsActivity::class.java)
                    startActivity(intent) }
                R.id.miItem3 -> { val intent = Intent(this@UserProfileActivity, UserProfileActivity::class.java)
                    startActivity(intent) }

            }
            binding.drawerLayout.closeDrawer(binding.navView)
            true
        }
    }
}