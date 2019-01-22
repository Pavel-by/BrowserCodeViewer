package com.example.browsercodeviewer.activity

import android.content.Intent
import android.net.sip.SipSession
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentContainer
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import com.example.browsercodeviewer.R
import com.example.browsercodeviewer.fragment.FragmentBody
import com.example.browsercodeviewer.fragment.FragmentHeaders
import com.example.browsercodeviewer.fragment.FragmentLoading
import com.example.browsercodeviewer.web.Request
import com.example.browsercodeviewer.web.Response
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_loading.*
import java.lang.Exception

const val TAG_FRAGMENT_LOADING = "fragment_loading"
const val TAG_FRAGMENT_HEADERS = "fragment_headers"
const val TAG_FRAGMENT_BODY = "fragment_body"
const val TAG_FRAGMENT_BROWSER = "fragment_browser"
const val TAG_FRAGMENT_BROWSER_CODE = "fragment_browser_code"
const val TAG_FRAGMENT_BROWSER_RESOURCES = "fragment_resources"

const val INTENT_KEY_URL = "url"
const val INTENT_KEY_RESPONSE_ID = "response_id"
const val INVALID_RESPONSE_ID = -1

/**
 * При старте активити подается один из параметров: url запроса или id сохраненного response
 */
class ActivityMain : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var response: Response? = null

    private var currentFragment: Fragment? = null
        set(fragment) {
            if (fragment == null)
                return
            val transaction = supportFragmentManager.beginTransaction()
            transaction.apply {
                replace(fragmentContainer.id, fragment)
                //else add(fragmentContainer.id, fragment, fragment.tag)
            }.commit()
            field = fragment
        }

    private val fragmentHeaders: FragmentHeaders = FragmentHeaders()
    private val fragmentBody: Fragment = FragmentBody()
    private val fragmentBrowser: Fragment? = null
    private val fragmentBrowserCode: Fragment? = null
    private val fragmentBrowserResources: Fragment? = null
    private val fragmentLoading: FragmentLoading = FragmentLoading()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentFragment = fragmentHeaders
        if (intent.action != null && intent.action == Intent.ACTION_VIEW && intent.data != null) {
            // Если активити вызвали для открытия ссылки через систему
            reload(intent.data!!.toString())
        } else if (intent.extras != null && intent.extras!!.getString(INTENT_KEY_URL) != null) {
            reload(intent.extras!!.getString(INTENT_KEY_URL)!!)
        } else if (intent.extras != null && intent.extras!!.getInt(INTENT_KEY_RESPONSE_ID, INVALID_RESPONSE_ID) != INVALID_RESPONSE_ID) {
            // TODO: реализовать сохранение состояния
        } else {
            throw Exception("Cannot find url to load")
        }

        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun reload() {
        if (response != null)
            reload(response!!.url)
        else
            throw Exception("Cannot reload url: url not found")
    }

    private fun reload(url: String) {
        val prevFragment = currentFragment
        currentFragment = fragmentLoading
        fragmentLoading.progress = 0
        val request = Request(url, object: Request.Listener {
            override fun onStart() {
            }

            override fun onProgress(progress: Float) {
                fragmentLoading.progress = (progress * 100).toInt()
            }

            override fun onSuccess(result: Response) {
                response = result
                currentFragment = prevFragment
            }

            override fun onError(error: Response.Error) {
                Toast.makeText(this@ActivityMain, error.data, Toast.LENGTH_SHORT).show()
            }
        })
        request.execute()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.headers -> {
                currentFragment = fragmentHeaders
            }

            R.id.body -> {
                currentFragment = fragmentBody
            }
        }
        drawer.closeDrawer(Gravity.START)
        return false
    }
}

