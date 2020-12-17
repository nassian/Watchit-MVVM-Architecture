package com.nassiansoft.watchit.data.network

import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.Flowable

interface RemoteDataSourceInterface {
    fun searchMovie(term: String): Flowable<Resource<List<Movie>>>
}