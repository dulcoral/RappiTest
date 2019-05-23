package com.coral.movietest.data.repository.db

import androidx.lifecycle.LiveData
import com.coral.movietest.data.repository.memory.MovieLocalDataSource
import com.coral.movietest.data.service.MovieApiResponse
import com.coral.movietest.models.Movie
import com.coral.movietest.models.MovieDetails
import com.coral.movietest.models.MovieRepo
import com.coral.movietest.models.Resource
import com.coral.movietest.util.AppExecutors
import com.coral.movietest.util.MovieType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val mLocalDataSource: MovieLocalDataSource,
    private val mRemoteDataSource: MovieRemoteDataSource,
    private val mExecutors: AppExecutors
) : DataRepository {

    override fun loadMovie(movieId: Long): LiveData<Resource<MovieDetails>> {
        return object : NetworkBoundResource<MovieDetails, Movie>(mExecutors) {

            override fun saveCallResult(item: Movie) {
                mLocalDataSource.saveMovie(item)
            }

            override fun shouldFetch(data: MovieDetails?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<MovieDetails> {
                return mLocalDataSource.getMovie(movieId)
            }

            override fun createCall(): LiveData<MovieApiResponse<Movie>> {
                return mRemoteDataSource.loadMovie(movieId)
            }

            override fun onFetchFailed() {
                //ignore
            }
        }.asLiveData()
    }
    override fun loadMoviesFilteredBy(sortBy: MovieType,word: String): MovieRepo {
        return mRemoteDataSource.loadMoviesFilteredBy(sortBy,word)
    }

}
