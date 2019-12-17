package com.mohseen78.countryfacts.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mohseen78.countryfacts.R
import com.mohseen78.countryfacts.model.Fact
import com.mohseen78.countryfacts.viewmodel.FactsApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    if(imgUrl != null){
        imgUrl.let {
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
    }else{
        imgView.visibility = View.GONE
    }
}

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