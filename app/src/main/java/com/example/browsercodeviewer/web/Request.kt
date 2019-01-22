package com.example.browsercodeviewer.web

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class Request(private val url: String, private val listener: Request.Listener): AsyncTask<Void, Float, Response>() {

    interface Listener {
        fun onStart()
        fun onProgress(progress: Float)
        fun onSuccess(result: Response)
        fun onError(error: Response.Error)
    }

    var headers: ArrayList<Header> = ArrayList()
    var method: RequestType = RequestType.GET

    override fun onPreExecute() {
        listener.onStart()
        if (!isOnline()) {
            listener.onError(Response.Error(0, "No internet connection"))
            cancel(true)
        }
    }

    override fun doInBackground(vararg p0: Void?): Response {
        val url = URL(url)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = this.method.toString()
        for (header in this.headers) {
            conn.setRequestProperty(header.name, header.data)
        }

        val response = Response(this.url, conn.responseCode)
        response.setHeaders(conn.headerFields)

        val input = conn.inputStream
        var br = BufferedReader(InputStreamReader(input, "UTF-8"))
        val builder = StringBuilder()
        var totalRead = 0
        var line:String? = br.readLine()
        while ((line) != null) {
            builder.append(line)
            totalRead += line.length
            line = br.readLine()
            publishProgress( totalRead.toFloat() / (input.available()))
        }

        response.data = builder.toString()

        return response
    }

    override fun onProgressUpdate(vararg values: Float?) {
        listener.onProgress(values[0]!!)
    }

    override fun onPostExecute(result: Response) {
        listener.onSuccess(result)
    }

    /*fun isInternetAvailable(): Boolean {
        try {
            val ipAddr = InetAddress.getByName("google.com")
            ipAddr.
            //You can replace it with your name
            return !ipAddr.hostAddress.equals("")

        } catch (e: Exception) {
            return false
        }

    }*/

    fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }
}