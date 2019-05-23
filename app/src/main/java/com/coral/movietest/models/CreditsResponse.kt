package com.coral.movietest.models

import com.google.gson.annotations.SerializedName

class CreditsResponse {

    @SerializedName("cast")
    var cast: List<Cast>? = null
}
