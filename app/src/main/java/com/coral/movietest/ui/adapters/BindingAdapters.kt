package com.coral.movietest.ui.adapters

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.coral.movietest.R
import com.coral.movietest.models.Genre
import com.coral.movietest.util.Constants
import com.coral.movietest.util.GlideApp
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl", "isBackdrop")
        fun bindImage(imageView: ImageView, imagePath: String?, isBackdrop: Boolean) {
            val baseUrl: String
            if (isBackdrop) {
                baseUrl = Constants.BACKDROP_URL
            } else {
                baseUrl = Constants.IMAGE_URL
            }

            GlideApp.with(imageView.context)
                .load(baseUrl + imagePath)
                .placeholder(R.color.black)
                .into(imageView)
        }

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun bindImage(imageView: ImageView, imagePath: String?) {
            GlideApp.with(imageView.context)
                .load(Constants.IMAGE_URL + imagePath)
                .placeholder(R.color.black)
                .apply(
                    RequestOptions().transforms(
                        CenterCrop()
                    )
                )
                .into(imageView)
        }

        @BindingAdapter("visibleGone")
        @JvmStatic
        fun showHide(view: View, show: Boolean) {
            if (show)
                view.visibility = View.VISIBLE
            else
                view.visibility = View.GONE
        }

        @JvmStatic
        @BindingAdapter("items")
        fun setItems(view: ChipGroup, genres: List<Genre>?) {
            if (genres == null
                || view.childCount > 0
            )
                return
            val context = view.context
            for (genre in genres) {
                val chip = Chip(context)
                chip.text = genre.name
                chip.setTextColor(context.resources.getColor(R.color.white))
                chip.chipBackgroundColor = ColorStateList.valueOf(
                    context.resources.getColor(R.color.grey)
                )
                view.addView(chip)
            }
        }
    }
}
