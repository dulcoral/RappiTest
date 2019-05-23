package com.coral.movietest.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coral.movietest.data.repository.db.MovieRepository
import com.coral.movietest.ui.viewmodels.MoviesViewModel
import com.coral.movietest.ui.viewmodels.MovieDetailsViewModel

class ViewModelFactory private constructor(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {

            return MoviesViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {

            return MovieDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException(modelClass.name)
    }

    companion object {

        fun getInstance(repository: MovieRepository): ViewModelFactory {
            return ViewModelFactory(repository)
        }
    }
}