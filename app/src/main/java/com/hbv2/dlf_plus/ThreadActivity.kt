package com.hbv2.dlf_plus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ThreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
        val button: Button = findViewById(R.id.btnOpenMain)
        button.setOnClickListener {
            val i = Intent(this@ThreadActivity, MainActivity::class.java)
            startActivity(i)
        }
    }
}