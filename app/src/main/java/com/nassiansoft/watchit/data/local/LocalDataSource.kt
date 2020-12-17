package com.nassiansoft.watchit.data.local
import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class  LocalDataSource @Inject constructor(movieDataBase: MovieDataBase) {

    private val dao=movieDataBase.movieDao()

    fun insertMovie(movie: Movie)=dao.insertMovie(movie)
        .subscribeOn(Schedulers.io())

    fun getAllMovies()=dao.getAllMovies()
        .subscribeOn(Schedulers.io())

    fun getMovieById(id:String)=dao.getMovieById(id)


    fun updateMovie(movie: Movie)=dao.updateMovie(movie)
        .subscribeOn(Schedulers.io())

    fun deleteMovie(movie: Movie)=dao.deleteMovie(movie)
            .subscribeOn(Schedulers.io())

}