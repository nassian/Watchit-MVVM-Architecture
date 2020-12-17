package com.nassiansoft.watchit.data

import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.data.network.Resource
import io.reactivex.Completable
import io.reactivex.Flowable

interface MovieRepositoryInterface {

    fun searchMovies(term: String): Flowable<Resource<List<Movie>>>
    fun insertMovie(movie: Movie): Completable
    fun getAllMovies(): Flowable<List<Movie>>
    fun getMovieById(id: String): Movie?
    fun updateMovie(movie: Movie): Completable
    fun deleteMovie(movie: Movie):Completable
}