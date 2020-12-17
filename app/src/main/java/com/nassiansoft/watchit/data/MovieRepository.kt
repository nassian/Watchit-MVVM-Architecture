package com.nassiansoft.watchit.data

import com.nassiansoft.watchit.data.local.LocalDataSource
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.data.network.RemoteDataSource
import com.nassiansoft.watchit.data.network.RemoteDataSourceInterface
import com.nassiansoft.watchit.data.network.Resource
import io.reactivex.Completable
import io.reactivex.Flowable

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSourceInterface
) : MovieRepositoryInterface {

    override fun searchMovies(term:String): Flowable<Resource<List<Movie>>> =
        remoteDataSource.searchMovie(term)
            .map {
              return@map  if (it is Resource.Success){
                  it.data?.map { movie->
                    movie.inMyList = getMovieById(movie.trackId) != null
                  }

                  it
                }else it
            }

    override fun insertMovie(movie:Movie)=localDataSource.insertMovie(movie)

    override fun getAllMovies()=localDataSource.getAllMovies()

    override fun getMovieById(id:String) =localDataSource.getMovieById(id)

    override fun updateMovie(movie: Movie)=localDataSource.updateMovie(movie)
    override fun deleteMovie(movie: Movie) =localDataSource.deleteMovie(movie)
}