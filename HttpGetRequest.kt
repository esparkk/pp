package com.example.passivedata

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpGetRequest : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String?): String {
        val urlString = params[0] ?: return "No URL provided"
        var result = ""
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(urlString)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"

            val responseCode = urlConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inStream = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val response = StringBuilder()

                var inputLine: String?
                while (inStream.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                inStream.close()
                result = response.toString()
            } else {
                result = "GET request failed. Response Code: $responseCode"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            result = e.message ?: "Unknown error"
        } finally {
            urlConnection?.disconnect()
        }
        return result
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        // 이곳에서 UI 업데이트 또는 결과 처리
        println("HTTP GET Response: $result")
    }
}
