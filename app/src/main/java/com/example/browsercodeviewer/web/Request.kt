package com.example.browsercodeviewer.web

import android.os.AsyncTask
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class Request(var url: String): AsyncTask<Void, Void, Response>() {
    var headers: ArrayList<Header> = ArrayList()
    var method: RequestType = RequestType.GET
    lateinit var listener: Response.Listener

    override fun doInBackground(vararg p0: Void?): Response {
        var url = URL(url)
        var conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = this.method.toString()
        for (header in this.headers) {
            conn.setRequestProperty(header.name, header.data)
        }

        var response = Response(conn.responseCode)
        response.setHeaders(conn.headerFields)

        var input = conn.inputStream
        var bufLen = 100
        var buffer = ByteArray(bufLen)
        var builder = StringBuilder()
        var read = input.read(buffer, 0, bufLen)
        while (read >= 0) {
            builder.append(buffer, 0, read)
            read = input.read(buffer, 0, bufLen)
        }

        response.data = builder.toString()

        return response
    }

    override fun onPostExecute(result: Response) {
        listener.onSuccess(result)
    }

    private fun headersMap() {
        var map = HashMap<String, String>()
    }
}