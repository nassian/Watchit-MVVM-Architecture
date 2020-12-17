package com.nassiansoft.watchit.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nassiansoft.watchit.data.MovieRepositoryInterface
import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.*

class HomeViewModelTest{

    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var repository:MovieRepositoryInterface
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun init(){
        repository=mock(MovieRepositoryInterface::class.java)
        homeViewModel= HomeViewModel(repository)
    }

    @Test
    fun testMyMovies_whenTheViewModelStarts(){

        //given
        val movie1=Movie("1","jafar1")
        val movie2=Movie("2","jafar2")
        val list= listOf(movie1,movie2)
        `when`(repository.getAllMovies()).thenReturn(
            Flowable.just(list)
        )


        //then
        val result=homeViewModel.getMyMovies()
        val observer= Observer<List<Movie>> {  }
        result.observeForever (observer)
        assertThat(result.value, `is`(list))

    }

    @Test
    fun testUpdateMovie(){

        //given
        val movie=Movie("1","jafar")
        `when`(repository.updateMovie(movie)).thenReturn(
            Completable.complete()
        )


        //when
        homeViewModel.markMovieAsWatched(movie)

        //then
        verify(repository).updateMovie(movie)

    }

}