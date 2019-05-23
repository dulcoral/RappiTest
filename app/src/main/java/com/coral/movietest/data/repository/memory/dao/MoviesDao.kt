package com.coral.movietest.data.repository.memory.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.coral.movietest.models.Movie
import com.coral.movietest.models.MovieDetails

@Dao
interface MoviesDao {

    @get:Query("SELECT * FROM movie WHERE is_favorite = 1")
    val allFavoriteMovies: LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movie: Movie)

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.id= :movieId")
    fun getMovie(movieId: Long): LiveData<MovieDetails>

}
