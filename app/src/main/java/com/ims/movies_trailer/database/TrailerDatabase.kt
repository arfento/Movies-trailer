package com.ims.movies_trailer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ims.movies_trailer.data.models.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(GenreIdsConverter::class)
abstract class TrailerDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}