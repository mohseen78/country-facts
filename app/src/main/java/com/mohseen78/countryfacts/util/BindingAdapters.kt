package com.mohseen78.countryfacts.util

import android.widget.ImageView
import android.widget.Toolbar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mohseen78.countryfacts.R
import com.mohseen78.countryfacts.adapter.FactsAdapter
import com.mohseen78.countryfacts.model.Fact
import com.mohseen78.countryfacts.viewmodel.FactsApiStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Fact>?) {
    val adapter = recyclerView.adapter as FactsAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

/**
 * Updates SwipeRefreshLayout based on FactsApiStatus Live Data
 */
@BindingAdapter("factsApiStatus")
fun bindStatus(swipeRefresh: SwipeRefreshLayout, status: FactsApiStatus?) {
    when (status) {
        FactsApiStatus.LOADING -> {
            swipeRefresh.isRefreshing = true
        }
        FactsApiStatus.ERROR -> {
            swipeRefresh.isRefreshing = false
        }
        FactsApiStatus.DONE -> {
            swipeRefresh.isRefreshing = false
        }
    }
}