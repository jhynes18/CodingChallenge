package com.inspiringapps.codingchallenge.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inspiringapps.codingchallenge.model.RequestSequence
import com.inspiringapps.codingchallenge.ui.main.RequestSequenceAdapter

@BindingAdapter("requestSequences")
fun bindRequestSequences(recyclerView: RecyclerView, requestSequences: List<RequestSequence>?)
{
    (recyclerView.adapter as RequestSequenceAdapter).updateRequestSequences(requestSequences)
}