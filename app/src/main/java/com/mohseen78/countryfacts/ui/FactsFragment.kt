package com.mohseen78.countryfacts.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.mohseen78.countryfacts.R
import com.mohseen78.countryfacts.adapter.FactsAdapter
import com.mohseen78.countryfacts.databinding.FragmentFactsBinding
import com.mohseen78.countryfacts.model.Fact
import com.mohseen78.countryfacts.util.ConnectivityReceiver
import com.mohseen78.countryfacts.viewmodel.FactsViewModel
import java.util.*

class FactsFragment : Fragment(), ConnectivityReceiver.ConnectivityReceiverListener {

    private lateinit var factsViewModel: FactsViewModel

    private lateinit var binding: FragmentFactsBinding

    private var mSnackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facts, container, false)

        // Allowing Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this


        //initialize FactsViewModel
        factsViewModel = ViewModelProviders.of(this).get(FactsViewModel::class.java)

        // Giving the binding access to the FactsViewModel
        binding.viewModel = factsViewModel

        factsViewModel.properties.observe(viewLifecycleOwner, Observer { facts ->
            binding.factsRv.adapter = FactsAdapter(facts)

        })

        initSwipeToRefresh()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }

    @SuppressLint("WrongConstant")
    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            val messageToUser = getString(R.string.snackbar_msg)
            mSnackBar = Snackbar.make(
                activity!!.findViewById(R.id.main_layout),
                messageToUser,
                Snackbar.LENGTH_LONG
            )
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
        } else {
            mSnackBar?.dismiss()
            factsViewModel.refreshFacts()
        }
    }

    // adding Listener to SwipeToRefresh
    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            factsViewModel.refreshFacts()
        }
    }
}