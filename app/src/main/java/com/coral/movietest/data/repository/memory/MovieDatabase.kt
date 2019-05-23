package com.coral.movietest.data.repository.memory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coral.movietest.data.repository.memory.dao.CastDao
import com.coral.movietest.data.repository.memory.dao.MoviesDao
import com.coral.movietest.data.repository.memory.dao.ReviewsDao
import com.coral.movietest.data.repository.memory.dao.TrailersDao
import com.coral.movietest.models.Cast
import com.coral.movietest.models.Movie
import com.coral.movietest.models.Review
import com.coral.movietest.models.Trailer
import com.coral.movietest.util.Converters
import javax.inject.Singleton

@Database(entities = [Movie::class, Trailer::class, Cast::class, Review::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
@Singleton
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    abstract fun trailersDao(): TrailersDao

    abstract fun castsDao(): CastDao

    abstract fun reviewsDao(): ReviewsDao

    companion object {

        val DATABASE_NAME = "Movies.db"

        fun buildDatabase(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}