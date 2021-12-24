package com.ims.movies_trailer.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.ims.movies_trailer.data.models.MovieType


class GenreIdsConverter {

    @TypeConverter
    fun genreToJson(value : List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value : String) = Gson().fromJson(value, Array<Int>::class.java).toList()

    @TypeConverter
    fun movieTypeToString(value : MovieType) = value.name

    @TypeConverter
    fun stringToMovieType(value: String) = MovieType.valueOf(value)

}