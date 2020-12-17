package com.nassiansoft.watchit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nassiansoft.watchit.data.model.Movie

@Database(entities = [Movie::class],version = 1,exportSchema = false)
abstract class MovieDataBase:RoomDatabase() {

    abstract fun movieDao():MovieDao
}