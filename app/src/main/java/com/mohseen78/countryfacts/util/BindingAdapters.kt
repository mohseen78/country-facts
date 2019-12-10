package com.mohseen78.countryfacts.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mohseen78.countryfacts.R
import com.mohseen78.countryfacts.adapter.FactsAdapter
import com.mohseen78.countryfacts.model.Fact

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Fact>?) {
    val adapter = recyclerView.adapter as FactsAdapter
    adapter.submitList(data)
}

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