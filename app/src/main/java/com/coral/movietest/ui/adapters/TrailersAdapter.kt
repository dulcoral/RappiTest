package com.coral.movietest.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.R
import com.coral.movietest.databinding.ItemTrailerBinding
import com.coral.movietest.models.Trailer
import com.coral.movietest.util.Constants
import com.coral.movietest.util.GlideApp

class TrailersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var trailerList: List<Trailer>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrailerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val trailer = trailerList!![position]
        (holder as TrailerViewHolder).bindTo(trailer)
    }

    override fun getItemCount(): Int {
        return if (trailerList != null) trailerList!!.size else 0
    }

    fun submitList(trailers: List<Trailer>?) {
        trailerList = trailers
        notifyDataSetChanged()
    }

    class TrailerViewHolder(private val binding: ItemTrailerBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun bindTo(trailer: Trailer) {
            val thumbnail = "https://img.youtube.com/vi/" + trailer.key + "/hqdefault.jpg"
            GlideApp.with(context)
                .load(thumbnail)
                .placeholder(R.color.black)
                .into(binding.imageTrailer)

            binding.trailerName.setText(trailer.title)

            binding.cardTrailer.setOnClickListener(View.OnClickListener {
                val appIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:" + trailer.key)
                )
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(Constants.YOUTUBE_WEB_URL + trailer.key)
                )
                if (appIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(appIntent)
                } else {
                    context.startActivity(webIntent)
                }
            })

            binding.executePendingBindings()
        }

        companion object {

            fun create(parent: ViewGroup): TrailerViewHolder {
                // Inflate
                val layoutInflater = LayoutInflater.from(parent.context)
                // Create the binding
                val binding = ItemTrailerBinding.inflate(layoutInflater, parent, false)
                return TrailerViewHolder(binding, parent.context)
            }
        }
    }

}