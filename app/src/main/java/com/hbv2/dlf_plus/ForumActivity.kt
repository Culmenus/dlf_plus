package com.hbv2.dlf_plus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ForumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)
        val dummyBackButton: Button = findViewById(R.id.btnOpenMain)
        dummyBackButton.setOnClickListener {
            val i = Intent(this@ForumActivity, MainActivity::class.java)
            startActivity(i)
        }

        val dummyThreadButton : Button = findViewById(R.id.dummybtnOpenThread);
        dummyThreadButton.setOnClickListener {
            val intent = Intent(this@ForumActivity, ThreadActivity::class.java)
            startActivity(intent)
        }
    }
}