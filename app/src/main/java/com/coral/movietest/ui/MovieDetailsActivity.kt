package com.coral.movietest.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coral.movietest.R
import com.coral.movietest.models.MovieDetails
import com.coral.movietest.models.Resource
import com.coral.movietest.util.Injection
import com.coral.movietest.util.UiUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import androidx.databinding.DataBindingUtil
import com.coral.movietest.databinding.ActivityDetailsBinding
import com.coral.movietest.ui.adapters.CastAdapter
import com.coral.movietest.ui.adapters.ReviewsAdapter
import com.coral.movietest.ui.adapters.TrailersAdapter
import com.coral.movietest.ui.viewmodels.MovieDetailsViewModel
import com.coral.movietest.util.Constants


class MovieDetailsActivity : AppCompatActivity() {

    private var mBinding: ActivityDetailsBinding? = null

    private var mViewModel: MovieDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppThemeLight)
        super.onCreate(savedInstanceState)
        val movieId = intent.getLongExtra(EXTRA_MOVIE_ID, DEFAULT_ID.toLong())
        if (movieId == DEFAULT_ID.toLong()) {
            closeOnError()
            return
        }

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mBinding?.lifecycleOwner = this

        mViewModel = obtainViewModel()
        mViewModel?.init(movieId)
        setupToolbar()
        setupTrailersAdapter()
        setupCastAdapter()
        setupReviewsAdapter()
        mViewModel?.result?.observe(this, Observer<Resource<MovieDetails>> { resource ->
            if (resource.data?.movie != null) {
                mViewModel!!.isFavorite = resource.data.movie!!.isFavorite
                invalidateOptionsMenu()
            }
            mBinding!!.resource = resource
            mBinding!!.setMovieDetails(resource.data)
        })
        mBinding?.networkState?.retryButton?.setOnClickListener { mViewModel!!.retry(movieId) }
        mViewModel?.snackbarMessage!!.observe(this,
            Observer { message -> Snackbar.make(mBinding?.root!!, message, Snackbar.LENGTH_SHORT).show() })
    }

    private fun setupToolbar() {
        val toolbar = mBinding?.toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            handleCollapsedToolbarTitle()
        }
    }

    private fun setupTrailersAdapter() {
        val listTrailers = mBinding?.movieDetailsInfo?.listTrailers!!
        listTrailers.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        listTrailers.setHasFixedSize(true)
        listTrailers.adapter = TrailersAdapter()
        ViewCompat.setNestedScrollingEnabled(listTrailers, false)
    }

    private fun setupCastAdapter() {
        val listCast = mBinding?.movieDetailsInfo?.listCast!!
        listCast?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        listCast?.adapter = CastAdapter()
        ViewCompat.setNestedScrollingEnabled(listCast, false)
    }

    private fun setupReviewsAdapter() {
        val listReviews = mBinding?.movieDetailsInfo?.listReviews!!
        listReviews?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listReviews?.adapter = ReviewsAdapter()
        ViewCompat.setNestedScrollingEnabled(listReviews, false)
    }

    private fun obtainViewModel(): MovieDetailsViewModel {
        val factory = Injection.provideViewModelFactory(this)
        return ViewModelProviders.of(this, factory).get(MovieDetailsViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movie_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                val movieDetails = mViewModel?.result?.getValue()?.data
                val shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setSubject(movieDetails?.movie?.title + " movie trailer")
                    .setText(
                        "Check out " + movieDetails?.movie?.title + " movie trailer at " +
                                Uri.parse(Constants.YOUTUBE_WEB_URL + movieDetails?.trailers?.get(0)?.key)
                    )
                    .createChooserIntent()

                var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                flags = flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT

                shareIntent.addFlags(flags)
                if (shareIntent.resolveActivity(packageManager) != null) {
                    startActivity(shareIntent)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun closeOnError() {
        throw IllegalArgumentException("Access denied.")
    }


    private fun handleCollapsedToolbarTitle() {
        mBinding?.appbar?.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    mBinding?.collapsingToolbar?.title = mViewModel?.result?.getValue()?.data?.movie?.title
                    isShow = true
                } else if (isShow) {
                    mBinding?.collapsingToolbar?.title = " "
                    isShow = false
                }
            }
        })
    }

    companion object {

        val EXTRA_MOVIE_ID = "extra_movie_id"

        private val DEFAULT_ID = -1
    }
}
