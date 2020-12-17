package com.nassiansoft.watchit.data.local

import androidx.room.*
import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie:Movie):Completable

    @Query("select * from movie")
    fun getAllMovies():Flowable<List<Movie>>

    @Query("select * from movie where trackId=:id")
    fun getMovieById(id:String):Movie?

    @Update
    fun updateMovie(movie: Movie):Completable


    @Delete
    fun deleteMovie(movie: Movie):Completable


}