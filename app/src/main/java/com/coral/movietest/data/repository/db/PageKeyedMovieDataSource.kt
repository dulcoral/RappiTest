package com.coral.movietest.data.repository.db

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.coral.movietest.data.service.MovieApiService
import com.coral.movietest.models.Movie
import com.coral.movietest.models.MovieList
import com.coral.movietest.models.Resource
import com.coral.movietest.util.MovieType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import java.util.concurrent.Executor

class PageKeyedMovieDataSource(
    private val movieService: MovieApiService,
    private val networkExecutor: Executor,
    private val sortBy: MovieType,
    private val word: String
) : PageKeyedDataSource<Int, Movie>() {

    private val FIRST_PAGE = 1
    var networkState = MutableLiveData<Any>()
    var retryCallback: (() -> Any)? = null


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(Resource.loading(null))

        val request: Call<MovieList> = when {
            sortBy === MovieType.POPULAR -> movieService.getMovies("popular", FIRST_PAGE)
            sortBy === MovieType.TOP_RATED -> movieService.getMovies("top_rated", FIRST_PAGE)
            sortBy === MovieType.UPCOMING -> movieService.getMovies("upcoming", FIRST_PAGE)
            else -> movieService.searchMovie(Locale.getDefault().language, word,FIRST_PAGE)
        }

        try {
            val response = request.execute()
            val data = response.body()
            val movieList = if (data != null) data.movies else mutableListOf()

            retryCallback = null
            networkState.postValue(Resource.success(null))
            callback.onResult(movieList!!, null, FIRST_PAGE + 1)
        } catch (e: IOException) {
            retryCallback = {
                networkExecutor.execute { loadInitial(params, callback) }

            }
            networkState.postValue(Resource.error(e.message!!, null))
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        networkState.postValue(Resource.loading(null))

        val request: Call<MovieList>
        if (sortBy === MovieType.POPULAR) {
            request = movieService.getMovies("popular", params.key)
        } else if (sortBy === MovieType.TOP_RATED) {
            request = movieService.getMovies("top_rated", params.key)
        } else if (sortBy === MovieType.UPCOMING) {
            request = movieService.getMovies("upcoming", params.key)
        } else {
            request = movieService.searchMovie(Locale.getDefault().language, word,params.key)
        }

        request.enqueue(object : Callback<MovieList> {
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                if (response.isSuccessful()) {
                    val data = response.body()
                    val movieList = if (data != null) data.movies else mutableListOf()

                    retryCallback = null
                    callback.onResult(movieList!!, params.key + 1)
                    networkState.postValue(Resource.success(null))
                } else {
                    retryCallback = {
                        loadAfter(params, callback)

                    }
                    networkState.postValue(Resource.error("error code: " + response.code(), null))
                }
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                retryCallback = {
                    networkExecutor.execute { loadAfter(params, callback) }

                }
                networkState.postValue(Resource.error(t.message!!, null))
            }
        })
    }

}
