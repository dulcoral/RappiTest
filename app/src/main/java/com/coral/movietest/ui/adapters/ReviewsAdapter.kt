package com.coral.movietest.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.coral.movietest.databinding.ItemReviewBinding
import com.coral.movietest.models.Review

class ReviewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var reviewList: List<Review>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val review = reviewList!![position]
        (holder as ReviewsViewHolder).bindTo(review)
    }

    override fun getItemCount(): Int {
        return if (reviewList != null) reviewList!!.size else 0
    }

    fun submitList(reviews: List<Review>?) {
        reviewList = reviews
        notifyDataSetChanged()
    }
    class ReviewsViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        init {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.frame.setClipToOutline(false)
            }
        }

        fun bindTo(review: Review) {
            val userName = review.author

            // review user image
            val generator = ColorGenerator.MATERIAL
            val color = generator.randomColor
            val drawable = TextDrawable.builder()
                .buildRound(userName?.substring(0, 1)?.toUpperCase(), color)
            binding.imageAuthor.setImageDrawable(drawable)

            // review's author
            binding.textAuthor.setText(userName)

            // review's content
            binding.textContent.setText(review.content)

            binding.executePendingBindings()
        }

        companion object {

            fun create(parent: ViewGroup): ReviewsViewHolder {
                // Inflate
                val layoutInflater = LayoutInflater.from(parent.context)
                // Create the binding
                val binding = ItemReviewBinding.inflate(layoutInflater, parent, false)
                return ReviewsViewHolder(binding)
            }
        }
    }
}
