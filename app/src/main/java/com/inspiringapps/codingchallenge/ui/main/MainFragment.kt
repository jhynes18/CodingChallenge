package com.inspiringapps.codingchallenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inspiringapps.codingchallenge.BR
import com.inspiringapps.codingchallenge.R
import com.inspiringapps.codingchallenge.databinding.MainFragmentBinding

class MainFragment : Fragment()
{
    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    private val adapter = RequestSequenceAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.setVariable(BR.viewModel, viewModel)

        binding.recyclerView.adapter = adapter

        return binding.root
    }

    companion object
    {
        fun newInstance() = MainFragment()
    }
}