package com.mohseen78.countryfacts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.mohseen78.countryfacts.R
import com.mohseen78.countryfacts.adapter.FactsAdapter
import com.mohseen78.countryfacts.databinding.FragmentFactsBinding
import com.mohseen78.countryfacts.viewmodel.FactsViewModel

class FactsFragment : Fragment() {

    private lateinit var factsViewModel: FactsViewModel

    private lateinit var binding: FragmentFactsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts, container, false)

        binding.lifecycleOwner = this

        factsViewModel = ViewModelProviders.of(this).get(FactsViewModel::class.java)

        binding.viewModel = factsViewModel

//        (activity as AppCompatActivity).supportActionBar!!.title = factsViewModel.title.toString()

        binding.factsRv.adapter= FactsAdapter()

        initSwipeToRefresh()

        return binding.root
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            factsViewModel.refreshFacts()
        }
    }
}