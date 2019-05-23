package com.coral.movietest.data.service

import androidx.lifecycle.LiveData
import com.coral.movietest.models.Movie
import com.coral.movietest.models.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    //top_rated,popular,upcoming
    @GET("movie/{movie_id}")
    fun getMovies(@Path("movie_id") movie: String, @Query("page") page: Int): Call<MovieList>

    @GET("movie/{id}?append_to_response=videos,credits,reviews")
    fun getMovieDetails(@Path("id") id: Long): LiveData<MovieApiResponse<Movie>>

    @GET("search/movie")
    fun searchMovie(@Query("language") language: String, @Query("query") query: String, @Query("page") page: Int): Call<MovieList>
}
