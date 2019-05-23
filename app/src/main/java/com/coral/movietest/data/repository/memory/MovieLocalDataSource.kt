package com.coral.movietest.data.repository.memory

import androidx.lifecycle.LiveData
import com.coral.movietest.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieLocalDataSource @Inject constructor(private val mDatabase: MovieDatabase) {

    fun saveMovie(movie: Movie) {
        mDatabase.moviesDao().insertMovie(movie)
        movie.trailerList?.trailers?.let { insertTrailers(it, movie.id) }
        movie.creditsResponse?.cast?.let { insertCastList(it, movie.id) }
        movie.reviewList?.reviews?.let { insertReviews(it, movie.id) }
    }

    private fun insertReviews(reviews: List<Review>, movieId: Long) {
        for (review in reviews) {
            review.movieId = movieId
        }
        mDatabase.reviewsDao().insertAllReviews(reviews)
    }

    private fun insertCastList(castList: List<Cast>, movieId: Long) {
        for (cast in castList) {
            cast.movieId = movieId
        }
        mDatabase.castsDao().insertAllCasts(castList)
    }

    private fun insertTrailers(trailers: List<Trailer>, movieId: Long) {
        for (trailer in trailers) {
            trailer.movieId = movieId
        }
        mDatabase.trailersDao().insertAllTrailers(trailers)
    }

    fun getMovie(movieId: Long): LiveData<MovieDetails> {
        return mDatabase.moviesDao().getMovie(movieId)
    }

}
