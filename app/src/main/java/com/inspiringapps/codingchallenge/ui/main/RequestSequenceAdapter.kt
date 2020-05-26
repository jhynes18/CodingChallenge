package com.inspiringapps.codingchallenge.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.inspiringapps.codingchallenge.R
import com.inspiringapps.codingchallenge.databinding.CardRequestSequenceBinding
import com.inspiringapps.codingchallenge.model.RequestSequence

class RequestSequenceAdapter : RecyclerView.Adapter<ViewHolder>()
{
    private var requestSequences = listOf<RequestSequence>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder
    {
        return RequestSequenceViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.card_request_sequence, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int)
    {
        (viewHolder as RequestSequenceViewHolder).bind(requestSequences[position])
    }

    override fun getItemCount() = requestSequences.size

    fun updateRequestSequences(requestSequences: List<RequestSequence>?)
    {
        requestSequences?.let {
            this.requestSequences = it
            notifyDataSetChanged()
        }
    }

    private class RequestSequenceViewHolder(itemView: View) : ViewHolder(itemView)
    {
        private val binding: CardRequestSequenceBinding? = DataBindingUtil.bind(itemView)

        fun bind(requestSequence: RequestSequence)
        {
            binding?.countTextView?.text = requestSequence.count.toString()
            binding?.request1TextView?.text = requestSequence.request1
            binding?.request2TextView?.text = requestSequence.request2
            binding?.request3TextView?.text = requestSequence.request3
        }
    }
}