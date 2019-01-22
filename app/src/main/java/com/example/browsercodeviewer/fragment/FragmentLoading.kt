package com.example.browsercodeviewer.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.browsercodeviewer.R
import kotlinx.android.synthetic.main.fragment_loading.*

class FragmentLoading : Fragment() {
    var progress = 0
        set(progress) {
            field = progress
            if (progressText != null)
                progressText.text = String.format("%d%% loaded", progress)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar.max = 100
        progressBar.progress = 0
    }
}