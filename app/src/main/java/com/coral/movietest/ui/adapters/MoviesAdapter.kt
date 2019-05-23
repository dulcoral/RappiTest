package com.coral.movietest.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.R
import com.coral.movietest.databinding.ItemMovieBinding
import com.coral.movietest.databinding.ItemNetworkStateBinding
import com.coral.movietest.models.Movie
import com.coral.movietest.models.Resource
import com.coral.movietest.ui.MovieDetailsActivity
import com.coral.movietest.ui.viewmodels.MoviesViewModel

class MoviesAdapter constructor(private val mViewModel: MoviesViewModel) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(MOVIE_COMPARATOR) {

    private var resource: Resource<Any>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            R.layout.item_movie -> return MovieViewHolder.create(parent)
            R.layout.item_network_state -> return NetworkStateViewHolder.create(parent, mViewModel)
            else -> throw IllegalArgumentException(viewType.toString())
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_movie -> (holder as MovieViewHolder).bindTo(getItem(position)!!)
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(resource!!)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_movie
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return resource != null && resource!!.status !== Resource.Status.SUCCESS
    }

    fun setNetworkState(resource: Resource<Any>) {
        val previousState = this.resource
        val hadExtraRow = hasExtraRow()
        this.resource = resource
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && !previousState!!.equals(resource)) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {

        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id.equals(newItem.id)
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        fun bindTo(movie: Movie) {
            binding.setMovie(movie)
            binding.getRoot().setOnClickListener { view ->
                val intent = Intent(view.context, MovieDetailsActivity::class.java)
                intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE_ID, movie.id)
                view.context.startActivity(intent)
            }

            binding.executePendingBindings()
        }

        companion object {

            fun create(parent: ViewGroup): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding)
            }
        }
    }

    class NetworkStateViewHolder(
        private val binding: ItemNetworkStateBinding,
        viewModel: MoviesViewModel
    ) : RecyclerView.ViewHolder(binding.getRoot()) {

        init {
            binding.retryButton.setOnClickListener { viewModel.retry() }
        }

        fun bindTo(resource: Resource<Any>) {
            binding.setResource(resource)
            binding.executePendingBindings()
        }

        companion object {

            fun create(parent: ViewGroup, viewModel: MoviesViewModel): NetworkStateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
                return NetworkStateViewHolder(binding, viewModel)
            }
        }
    }
}
