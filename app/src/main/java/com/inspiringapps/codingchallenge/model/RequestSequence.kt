package com.inspiringapps.codingchallenge.model

import java.util.*

data class RequestSequence(var request1: String?, var request2: String?, var request3: String?, var count: Int = 1)
{
    constructor(queue: Queue<String>) : this(queue.elementAt(0), queue.elementAt(1), queue.elementAt(2))

    fun doesQueueMatchRequestSequence(queue: Queue<String>): Boolean
    {
        return queue.elementAt(0) == request1 && queue.elementAt(1) == request2 && queue.elementAt(2) == request3
    }
}