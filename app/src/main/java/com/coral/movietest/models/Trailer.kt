package com.coral.movietest.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "trailer",
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )],
    indices = [Index(value = ["movie_id"])]
)
class Trailer {

    @PrimaryKey
    @SerializedName("id")
    var id: String = ""

    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0

    @SerializedName("key")
    var key: String? = null

    @SerializedName("site")
    var site: String? = null

    @SerializedName("name")
    var title: String? = null
}
