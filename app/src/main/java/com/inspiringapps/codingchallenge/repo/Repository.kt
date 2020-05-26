package com.inspiringapps.codingchallenge.repo

import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.IOException

class Repository
{
    private val client = OkHttpClient()

    val networkState = MutableLiveData<NetworkState>()
    val logFileLines = MutableLiveData<List<String>>()

    fun getLogFile()
    {
        networkState.value = NetworkState.LOADING

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback
        {
            override fun onFailure(call: okhttp3.Call, e: IOException)
            {
                networkState.postValue(NetworkState.ERROR)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response)
            {
                if (response.isSuccessful)
                {
                    logFileLines.postValue(response.body?.byteStream()?.bufferedReader()?.use(BufferedReader::readLines))
                    networkState.postValue(NetworkState.SUCCESS)
                }
                else
                {
                    networkState.postValue(NetworkState.ERROR)
                }
            }
        })
    }

    enum class NetworkState
    {
        LOADING,
        SUCCESS,
        ERROR
    }

    companion object
    {
        private const val url = "http://dev.inspiringapps.com/Files/IAChallenge/30E02AAA-B947-4D4B-8FB6-9C57C43872A9/Apache.log"
    }
}