package com.example.browsercodeviewer.web

import android.text.TextUtils

class Response(var code: Int) {
    interface Listener {
        fun onSuccess(response: Response)
        fun onError(response: Response)
    }

    class Error(var code: Int, var data: String)

    var data: String = ""
    var headers: ArrayList<Header> = ArrayList()
    var error: Error? = null

    fun setHeaders(headers: Map<String, List<String>>) {
        for (headerName in headers.keys) {
            this.headers.add(Header(headerName, TextUtils.join("; ", headers.get(headerName))))
        }
    }
}