package com.nassiansoft.watchit.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nassiansoft.watchit.data.MovieRepositoryInterface
import com.nassiansoft.watchit.data.model.Movie
import com.nassiansoft.watchit.data.network.Resource
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class SearchViewModelTest{

    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var repository:MovieRepositoryInterface
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup(){
        repository=mock(MovieRepositoryInterface::class.java)

        viewModel=SearchViewModel(repository)
    }

    @Test
    fun testSearch_whenResponseIsSuccessful(){

        //given
        val list= listOf(Movie("1","jafar"))
        `when`(repository.searchMovies(anyString()))
            .thenReturn(Flowable.just(Resource.Success(list)))

        //when
        viewModel.search()

        //then
        val observer=Observer<Resource<List<Movie>>>{}
        val liveResource=viewModel.getSearchList()
        liveResource.observeForever(observer)
        assertEquals(list,liveResource.value?.data)

    }

    @Test
    fun testSearch_whenResponseIsNotSuccessful(){

        //given
        val message="Failed"
        `when`(repository.searchMovies(anyString()))
            .thenReturn(Flowable.just(Resource.Error(message)))

        //when
        viewModel.search()

        //then
        val observer=Observer<Resource<List<Movie>>>{}
        val liveResource=viewModel.getSearchList()
        liveResource.observeForever(observer)
        assertEquals(message,liveResource.value?.message)

    }

    @Test
    fun testSaveMovie(){

        //given
        val movie=Movie("1","jafar")
        `when`(repository.insertMovie(movie))
            .thenReturn(Completable.complete())

        //when
        viewModel.saveMovie(movie)

        //then
        Mockito.verify(repository).insertMovie(movie)

    }



}