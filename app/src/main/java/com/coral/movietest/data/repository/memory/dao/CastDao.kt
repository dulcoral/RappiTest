package com.coral.movietest.data.repository.memory.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.coral.movietest.models.Cast

@Dao
interface CastDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCasts(castList: List<Cast>)

}