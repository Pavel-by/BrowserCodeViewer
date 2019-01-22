package com.example.browsercodeviewer.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.browsercodeviewer.R
import com.example.browsercodeviewer.activity.ActivityMain
import com.example.browsercodeviewer.web.Response
import kotlinx.android.synthetic.main.fragment_body.*

class FragmentBody() : Fragment() {

    init {
        this.retainInstance = true
    }

    var savedResponse:Response? = null
    var savedRootView:View? = null

    var text = ""
        set(value) {
            if (textView != null) textView.text = value
            field = text
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedRootView == null)
            savedRootView = inflater.inflate(R.layout.fragment_body, container, false)
        return savedRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //textView.text = text
    }

    override fun onStart() {
        super.onStart()
        val response = getResponse()
        if (response != null && savedResponse != response) {
            savedResponse = response
            textView.text = response.data

        } else if (response == null) {
            textView.text = "Page not loaded"
        }
    }

    private fun getResponse(): Response? {
        if (context !is ActivityMain) {
            throw Exception("This fragment can be call only from ActivityMain!")
        }
        return (context as ActivityMain).response
    }
}