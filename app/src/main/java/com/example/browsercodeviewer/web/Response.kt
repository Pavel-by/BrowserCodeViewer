package com.example.browsercodeviewer.web

import android.text.TextUtils

class Response(var url: String, var code: Int) {
    class Error(var code: Int, var data: String)

    var data: String = ""
    var headers: ArrayList<Header> = ArrayList()
    var error: Error? = null

    fun setHeaders(headers: Map<String, List<String>>) {
        for (headerName in headers.keys) {
            if (headers.get(headerName) == null || headerName == null) continue
            val data = TextUtils.join("; ", headers.get(headerName)!!)
            this.headers.add(Header(headerName, data))
        }
    }
}