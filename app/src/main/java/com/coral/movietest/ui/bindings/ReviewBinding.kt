package com.coral.movietest.ui.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.models.Review
import com.coral.movietest.ui.adapters.ReviewsAdapter

object ReviewBinding {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<Review>?) {
        val adapter = recyclerView.adapter as ReviewsAdapter?
        adapter?.submitList(items)
    }
}
