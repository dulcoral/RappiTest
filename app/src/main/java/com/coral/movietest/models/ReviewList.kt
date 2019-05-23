package com.coral.movietest.models

import com.google.gson.annotations.SerializedName

class ReviewList {

    @SerializedName("results")
    var reviews: List<Review>? = null
}
