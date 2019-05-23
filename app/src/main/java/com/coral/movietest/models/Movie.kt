package com.coral.movietest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "movie")
class Movie {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Long = 0

    @SerializedName("title")
    var title: String? = null

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String? = null

    @SerializedName("overview")
    var overview: String? = null

    @SerializedName("original_language")
    var originalLanguage: String? = null
        get() = field!!.substring(0, 1).toUpperCase() + field!!.substring(1)

    @SerializedName("popularity")
    var popularity: Double = 0.toDouble()

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double = 0.toDouble()

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Int = 0

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: String? = null

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false

    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    var genres: List<Genre>? = null

    @Ignore
    @SerializedName("videos")
    var trailerList: TrailerList? = null

    @Ignore
    @SerializedName("credits")
    var creditsResponse: CreditsResponse? = null

    @Ignore
    @SerializedName("reviews")
    var reviewList: ReviewList? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass!= o.javaClass) return false
        val movie = o as Movie?
        return id == movie!!.id &&
                java.lang.Double.compare(movie.popularity, popularity) == 0 &&
                java.lang.Double.compare(movie.voteAverage, voteAverage) == 0 &&
                title == movie.title &&
                posterPath == movie.posterPath &&
                overview == movie.overview &&
                releaseDate == movie.releaseDate
    }

    override fun hashCode(): Int {
        return Objects.hash(id, title, posterPath, overview, popularity, voteAverage, releaseDate)
    }
}