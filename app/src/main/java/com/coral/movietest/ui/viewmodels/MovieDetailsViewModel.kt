package com.coral.movietest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.coral.movietest.data.repository.db.MovieRepository
import com.coral.movietest.models.MovieDetails
import com.coral.movietest.models.Resource
import com.coral.movietest.util.SnackbarMessage

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    var result: LiveData<Resource<MovieDetails>>? = null
        private set

    private val movieIdLiveData = MutableLiveData<Long>()

    val snackbarMessage = SnackbarMessage()

    var isFavorite: Boolean = false

    fun init(movieId: Long) {
        if (result != null) {
            return
        }

        result = Transformations.switchMap<Long, Resource<MovieDetails>>(
            movieIdLiveData
        ) { movieId -> repository.loadMovie(movieId) }

        setMovieIdLiveData(movieId)
    }

    private fun setMovieIdLiveData(movieId: Long) {
        movieIdLiveData.value = movieId
    }

    fun retry(movieId: Long) {
        setMovieIdLiveData(movieId)
    }

}
