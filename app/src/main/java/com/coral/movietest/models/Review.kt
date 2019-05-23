package com.coral.movietest.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "review",
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )],
    indices = [Index(value = ["movie_id"])]
)
class Review {

    @PrimaryKey
    @SerializedName("id")
    var id: String = ""

    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0

    @SerializedName("author")
    var author: String? = null

    @SerializedName("content")
    var content: String? = null

    @SerializedName("url")
    var url: String? = null
}
