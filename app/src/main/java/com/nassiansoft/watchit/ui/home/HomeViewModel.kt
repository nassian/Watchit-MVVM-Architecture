package com.nassiansoft.watchit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.nassiansoft.watchit.data.MovieRepositoryInterface
import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.Completable
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository:MovieRepositoryInterface)
    : ViewModel() {

    private val _myMovies by lazy {
        LiveDataReactiveStreams.fromPublisher(
            repository.getAllMovies()
        )
    }
    fun getMyMovies():LiveData<List<Movie>> =_myMovies

    fun markMovieAsWatched(movie: Movie): Completable {
        return repository.updateMovie(movie)
    }


}