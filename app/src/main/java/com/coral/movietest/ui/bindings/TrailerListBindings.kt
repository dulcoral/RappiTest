package com.coral.movietest.ui.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.models.Trailer
import com.coral.movietest.ui.adapters.TrailersAdapter

object TrailerListBindings {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<Trailer>?) {
        val adapter = recyclerView.adapter as TrailersAdapter?
        adapter?.submitList(items)
    }
}
