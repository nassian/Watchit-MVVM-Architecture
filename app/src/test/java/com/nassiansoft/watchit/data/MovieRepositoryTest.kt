package com.nassiansoft.watchit.data

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nassiansoft.watchit.BaseApp
import com.nassiansoft.watchit.RxSchedulerRule
import com.nassiansoft.watchit.data.local.LocalDataSource
import com.nassiansoft.watchit.data.local.MovieDataBase
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.data.network.RemoteDataSource
import com.nassiansoft.watchit.data.network.Resource
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MovieRepositoryTest {


    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private lateinit var repository: MovieRepository
    private lateinit var remoteDataSource: RemoteDataSource


    @Before
    fun init() {
        remoteDataSource = mock(RemoteDataSource::class.java)
        val app = ApplicationProvider.getApplicationContext<Application>()
        val movieDataBase = Room.inMemoryDatabaseBuilder(app, MovieDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        val localDataSource = LocalDataSource(movieDataBase)

        repository = MovieRepository(localDataSource, remoteDataSource)

    }

    @Test
    fun testInsertMovie() {

        //give
        val movie = Movie("1", "jafar")

        //when
        repository.insertMovie(movie).blockingAwait()


        //then
        repository.getAllMovies().test().assertValue {
            it.size == 1
        }
        repository.getAllMovies().test().assertValue {
            it[0] == movie
        }

    }

    @Test
    fun testGetMovieById() {

        //give
        val movie = Movie("1", "jafar")

        //when
        repository.insertMovie(movie).blockingAwait()


        //then
        repository.getMovieById(movie.trackId).let {
            it == movie
        }
    }


    @Test
    fun testGetMovieById_WhenTheMovieCanNotBeFound() {

        //give
        val movie = Movie("1", "jafar")

        //when
        repository.insertMovie(movie).blockingAwait()


        //then
        repository.getMovieById("2").let {
            it == null
        }
    }


    @Test
    fun testSearchMovie() {

        //given
        val movies = listOf(Movie("1", "jafar"))
        `when`(remoteDataSource.searchMovie(Mockito.any()))
            .thenReturn(Flowable.just(Resource.Success(movies)))

        //when
        val result = repository.searchMovies("hi")


        //then
        result.test().assertValue {
            it.data == movies
        }


    }

    @Test
    fun testUpdateMovie(){

        //given
        val movie=Movie("1","jafar")
        repository.insertMovie(movie)

        //when
        movie.trackName="jafar2"
        repository.updateMovie(movie).blockingAwait()


        //then
        repository.getMovieById(movie.trackId).let {
            it?.trackName=="jafar2"
        }

    }

    @Test
    fun testDeleteMovie(){

        //given
        val movie=Movie("1","jafar")
        repository.insertMovie(movie)


        //when
        repository.deleteMovie(movie)

        //then
        repository.getAllMovies().test().assertValue {
            it.isNullOrEmpty()
        }
    }


}