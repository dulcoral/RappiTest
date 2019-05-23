package com.coral.movietest.models

import com.google.gson.annotations.SerializedName

class Genre {

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String? = null
}
