package com.coral.movietest.util

import android.content.Context
import com.coral.movietest.data.repository.db.MovieRemoteDataSource
import com.coral.movietest.data.repository.db.MovieRepository
import com.coral.movietest.data.repository.memory.MovieDatabase
import com.coral.movietest.data.repository.memory.MovieLocalDataSource
import com.coral.movietest.data.service.MovieApiClient

object Injection {

    fun provideMoviesRemoteDataSource(): MovieRemoteDataSource {
        return MovieRemoteDataSource(
            MovieApiClient.instance.value.movieService,
            AppExecutors()
        )
    }

    fun provideMoviesLocalDataSource(context: Context): MovieLocalDataSource {
        val database = MovieDatabase.buildDatabase(context)
        return MovieLocalDataSource(database)
    }

    fun provideMovieRepository(context: Context): MovieRepository {
        val remoteDataSource = provideMoviesRemoteDataSource()
        val localDataSource = provideMoviesLocalDataSource(context)
        val executors = AppExecutors()
        return MovieRepository(
            localDataSource,
            remoteDataSource,
            executors
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val repository = provideMovieRepository(context)
        return ViewModelFactory.getInstance(repository)
    }
}