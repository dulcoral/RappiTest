package com.coral.movietest.ui.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.models.Cast
import com.coral.movietest.ui.adapters.CastAdapter

object CastBinding {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<Cast>?) {
        val adapter = recyclerView.adapter as CastAdapter
        adapter?.submitList(items)
    }

}