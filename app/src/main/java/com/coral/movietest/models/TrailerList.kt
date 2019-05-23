package com.coral.movietest.models

import com.google.gson.annotations.SerializedName

class TrailerList {

    @SerializedName("results")
    var trailers: List<Trailer>? = null
}
