package com.coral.movietest.data.repository.db

import androidx.lifecycle.LiveData
import com.coral.movietest.models.MovieDetails
import com.coral.movietest.models.MovieRepo
import com.coral.movietest.models.Resource
import com.coral.movietest.util.MovieType

interface DataRepository {

    fun loadMovie(movieId: Long): LiveData<Resource<MovieDetails>>

    fun loadMoviesFilteredBy(sortBy: MovieType, word: String): MovieRepo

}
