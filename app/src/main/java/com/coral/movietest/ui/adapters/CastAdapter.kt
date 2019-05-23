package com.coral.movietest.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.R
import com.coral.movietest.databinding.ItemCastBinding

import com.coral.movietest.models.Cast
import com.coral.movietest.util.Constants
import com.coral.movietest.util.GlideApp


class CastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var castList: List<Cast>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CastViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cast = castList?.get(position)
        (holder as CastViewHolder).bindTo(cast)
    }

    override fun getItemCount(): Int {
        return if (castList != null) castList!!.size else 0
    }

    fun submitList(casts: List<Cast>?) {
        castList = casts
        notifyDataSetChanged()
    }

    class CastViewHolder(private val binding: ItemCastBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.getRoot()) {

        fun bindTo(cast: Cast?) {
            val profileImage = Constants.IMAGE_BASE_URL + Constants.PROFILE_SIZE_W185 + cast?.profileImagePath
            GlideApp.with(context)
                .load(profileImage)
                .placeholder(R.drawable.ic_profile_24dp)
                .dontAnimate()
                .into(binding.imageCastProfile)

            binding.textCastName.setText(cast?.actorName)

            binding.executePendingBindings()
        }

        companion object {

            fun create(parent: ViewGroup): CastViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCastBinding.inflate(layoutInflater, parent, false)
                return CastViewHolder(binding, parent.context)
            }
        }
    }
}