package com.nassiansoft.watchit.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nassiansoft.watchit.IPlayerHelper
import com.nassiansoft.watchit.PlayerHelper
import com.nassiansoft.watchit.data.MovieRepositoryInterface
import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "DetailViewModel"
class DetailViewModel @Inject constructor(private val repository: MovieRepositoryInterface
,val playerHelper: IPlayerHelper)
    : ViewModel() {
    lateinit var movie: Movie
    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _movieDeleted = MutableLiveData<Boolean>().also {
        it.value=false
    }
    fun getMovieDeleted(): LiveData<Boolean> = _movieDeleted

    fun deleteMovie() {
        compositeDisposable.add(

            repository.deleteMovie(movie)
                .subscribe({ _movieDeleted.postValue(true) },
                    { Log.d(TAG, "deleteMovie: $it") })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}