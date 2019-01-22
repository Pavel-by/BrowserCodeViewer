package com.example.browsercodeviewer.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.browsercodeviewer.R
import com.example.browsercodeviewer.activity.ActivityMain
import com.example.browsercodeviewer.adapter.LVAHeaders
import com.example.browsercodeviewer.web.Header
import com.example.browsercodeviewer.web.Response
import kotlinx.android.synthetic.main.fragment_headers.*
import java.lang.Exception

class FragmentHeaders : Fragment() {

    var savedResponse: Response? = null
    var savedRootView: View? = null
    lateinit var adapter: LVAHeaders

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedRootView == null)
            savedRootView = inflater.inflate(R.layout.fragment_headers, container, false)
        return savedRootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity != null) {
            adapter = LVAHeaders(activity!!, null)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        val response = getResponse()
        if (response != null) {
            savedResponse = response
            responseBlock.visibility = View.VISIBLE
            adapter.headers = response.headers
            responseCode.text = response.code.toString()

        } else {
            responseBlock.visibility = View.GONE
            error.visibility = View.VISIBLE
            error.text = "Page not loaded"
        }
    }

    private fun getResponse(): Response? {
        if (context !is ActivityMain) {
            throw Exception("This fragment can be call only from ActivityMain!")
        }
        return (context as ActivityMain).response
    }
}
