package com.coral.movietest.data.repository.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.coral.movietest.data.service.MovieApiResponse
import com.coral.movietest.data.service.MovieApiService
import com.coral.movietest.models.Movie
import com.coral.movietest.models.MovieRepo
import com.coral.movietest.util.AppExecutors
import com.coral.movietest.util.MovieType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
    private val mService: MovieApiService,
    private val mExecutors: AppExecutors
) {
    private val PAGE_SIZE = 20


    fun loadMovie(movieId: Long): LiveData<MovieApiResponse<Movie>> {
        return mService.getMovieDetails(movieId)
    }

    fun loadMoviesFilteredBy(sortBy: MovieType, word: String): MovieRepo {
        val sourceFactory =
            MovieDataSourceFactory(mService, mExecutors.networkIO(), sortBy, word)

        // configure page
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()

        val moviesPagedList = LivePagedListBuilder(sourceFactory, config)
            .setFetchExecutor(mExecutors.networkIO())
            .build()

        val networkState = Transformations.switchMap(
            sourceFactory.sourceLiveData
        ) { input -> input.networkState }

        return MovieRepo(
            moviesPagedList,
            networkState,
            sourceFactory.sourceLiveData
        )
    }

}
