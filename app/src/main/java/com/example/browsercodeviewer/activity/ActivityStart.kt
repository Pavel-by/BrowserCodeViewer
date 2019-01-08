package com.example.browsercodeviewer.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.browsercodeviewer.R
import kotlinx.android.synthetic.main.activity_start.*

//import kotlinx.android.synthetic.main.fragment_web_view.*

class ActivityStart : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        button.setOnClickListener {
            run {
                startActivity(Intent(this@ActivityStart, ActivityMain::class.java))
            }
        }
        startActivity(Intent(this@ActivityStart, ActivityMain::class.java))
    }
}