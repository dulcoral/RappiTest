package com.coral.movietest.models

import com.google.gson.annotations.SerializedName

class MovieList {

    @SerializedName("page")
    var page: Int = 0

    @SerializedName("total_results")
    var totalResults: Int = 0

    @SerializedName("total_pages")
    var totalPages: Int = 0

    @SerializedName("results")
    var movies: MutableList<Movie>? = null
}