package com.coral.movietest.data.repository.db

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.coral.movietest.data.service.MovieApiService
import com.coral.movietest.models.Movie
import com.coral.movietest.util.MovieType
import java.util.concurrent.Executor

class MovieDataSourceFactory(
    private val mService: MovieApiService,
    private val networkExecutor: Executor,
    private val sortBy: MovieType,
    private val word: String
) : DataSource.Factory<Int, Movie>() {

    val sourceLiveData = MutableLiveData<PageKeyedMovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = PageKeyedMovieDataSource(mService, networkExecutor, sortBy, word)
        sourceLiveData.postValue(source)
        return source
    }
}