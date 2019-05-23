package com.coral.movietest.util

import androidx.room.TypeConverter
import com.coral.movietest.models.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    val gson = Gson()

    @TypeConverter
    fun fromGenresList(genres: List<Genre>): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenresList(genres: String?): List<Genre> {
        if (genres == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Genre>>() {}.type

        return gson.fromJson<List<Genre>>(genres, listType)
    }
}