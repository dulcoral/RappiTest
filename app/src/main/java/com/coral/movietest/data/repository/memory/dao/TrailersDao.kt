package com.coral.movietest.data.repository.memory.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.coral.movietest.models.Trailer

@Dao
interface TrailersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllTrailers(trailers: List<Trailer>)

}
