package com.coral.movietest.data.repository.memory.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.coral.movietest.models.Review

@Dao
interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllReviews(reviews: List<Review>)

}