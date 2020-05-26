package com.inspiringapps.codingchallenge.ui.main

import android.view.View
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.inspiringapps.codingchallenge.model.Request
import com.inspiringapps.codingchallenge.model.RequestSequence
import com.inspiringapps.codingchallenge.repo.Repository
import com.inspiringapps.codingchallenge.repo.Repository.NetworkState.LOADING
import java.util.*
import java.util.regex.Pattern

class MainViewModel : ViewModel()
{
    private val repo = Repository()

    val isLoading = Transformations.map(repo.networkState) {
        it == LOADING
    }

    private val pattern = Pattern.compile("^(\\S+) - - \\[.+] \"(\\w+ \\S+ \\S+)\"")

    val requestSequences = Transformations.map(repo.logFileLines) { list ->
        list.mapNotNull { parseToRequest(it) }.findRequestSequences().sortedByDescending { it.count }
    }

    fun getRequestSequences(view: View)
    {
        repo.getLogFile()
    }

    private fun parseToRequest(logLine: String): Request?
    {
        val matcher = pattern.matcher(logLine)
        val matchFound = matcher.find()

        val ipAddress = matcher.group(1)
        val request = matcher.group(2)

        return when (matchFound && !ipAddress.isNullOrEmpty() && !request.isNullOrEmpty())
        {
            true -> Request(ipAddress, request)
            else -> null
        }
    }

    private fun List<Request>.findRequestSequences(): List<RequestSequence>
    {
        val requestSequences = mutableListOf<RequestSequence>()
        val ipAddressWithPotentialSequence = hashMapOf<String, Queue<String>?>()

        this.forEach { request ->

            when (val requestQueue = ipAddressWithPotentialSequence[request.ipAddress])
            {
                null ->
                {
                    val newRequestQueue: Queue<String> = LinkedList()
                    newRequestQueue.add(request.request)
                    ipAddressWithPotentialSequence[request.ipAddress] = newRequestQueue
                }
                else ->
                {
                    requestQueue.add(request.request)

                    if (requestQueue.size == 3)
                    {
                        when (val requestSequence = requestSequences.find { it.doesQueueMatchRequestSequence(requestQueue) })
                        {
                            null -> requestSequences.add(RequestSequence(requestQueue))
                            else -> requestSequence.count++
                        }

                        requestQueue.remove()
                    }
                }
            }
        }

        return requestSequences
    }
}