package com.coral.movietest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.coral.movietest.R
import com.coral.movietest.data.repository.db.MovieRepository
import com.coral.movietest.models.Movie
import com.coral.movietest.models.MovieRepo
import com.coral.movietest.util.MovieType

class MoviesViewModel(movieRepository: MovieRepository) : ViewModel() {

    private var movieRepo: LiveData<MovieRepo>

    val pagedList: LiveData<PagedList<Movie>>

    val networkState: LiveData<Any>

    private val currentTitle = MutableLiveData<Int>()

    private val sortBy = MutableLiveData<MovieType>()

    private var sword: String = ""

    val currentSorting: MovieType?
        get() = sortBy.value

    init {
        sortBy.setValue(MovieType.POPULAR)
        currentTitle.setValue(R.string.popular_imenu)

        movieRepo = Transformations.map<MovieType, MovieRepo>(
            sortBy
        ) { sort -> movieRepository.loadMoviesFilteredBy(sort, sword) }

        pagedList = Transformations.switchMap<MovieRepo, PagedList<Movie>>(
            movieRepo
        ) { input -> input.data }

        networkState = Transformations.switchMap<MovieRepo, Any>(
            movieRepo
        ) { input -> input.resource }
    }

    fun getCurrentTitle(): LiveData<Int> {
        return currentTitle
    }

    fun setIndex(index: Int) {
        currentTitle.value = index
    }

    fun setSortMoviesBy(id: Int) {
        var filterType: MovieType?
        var title: Int?
        when (id) {
            R.id.popular_imenu -> {
                if (sortBy.value === MovieType.POPULAR)
                    return

                filterType = MovieType.POPULAR
                title = R.string.popular_imenu
            }
            R.id.top_rated_imenu -> {
                if (sortBy.value === MovieType.TOP_RATED)
                    return

                filterType = MovieType.TOP_RATED
                title = R.string.toprated_imenu
            }
            R.id.upcoming_imenu -> {
                if (sortBy.value === MovieType.UPCOMING)
                    return
                filterType = MovieType.UPCOMING
                title = R.string.upcoming_imenu
            }
            else -> throw IllegalArgumentException("unknown sorting id")
        }
        sortBy.value = filterType
        currentTitle.value = title
    }

    fun searchMovies(word: String) {
        currentTitle.value = R.string.search_menu_title
        sortBy.value = MovieType.SEARCH
        sword = word

    }

    fun retry() {
        movieRepo.getValue()!!.sourceLiveData.value?.retryCallback?.invoke()
    }
}