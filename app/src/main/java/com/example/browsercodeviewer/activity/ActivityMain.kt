package com.example.browsercodeviewer.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import com.example.browsercodeviewer.R
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer.isActivated = true
        //drawer.closeDrawer(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
}
