package com.nassiansoft.watchit.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.nassiansoft.watchit.data.MovieRepository
import com.nassiansoft.watchit.data.MovieRepositoryInterface
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.data.network.Resource
import io.reactivex.Completable
import javax.inject.Inject

private const val TAG = "SearchViewModel"
class SearchViewModel @Inject constructor(private val repository: MovieRepositoryInterface)
    : ViewModel() {

    var term=""
    private val _searchList=MediatorLiveData<Resource<List<Movie>>>()
    fun getSearchList():LiveData<Resource<List<Movie>>> =_searchList


    fun search(){

        _searchList.postValue(Resource.Loading())
        val result: LiveData<Resource<List<Movie>>> =LiveDataReactiveStreams
            .fromPublisher (repository.searchMovies(term))

        _searchList.addSource(result){
            _searchList.postValue(it)
            _searchList.removeSource(result)

        }
    }

    fun saveMovie(movie: Movie): Completable {
        movie.inMyList=true
       return repository.insertMovie(movie)
    }


}