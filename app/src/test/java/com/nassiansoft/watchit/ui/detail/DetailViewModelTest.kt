package com.nassiansoft.watchit.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.exoplayer2.ui.PlayerView
import com.nassiansoft.watchit.IPlayerHelper
import com.nassiansoft.watchit.PlayerHelper
import com.nassiansoft.watchit.data.MovieRepositoryInterface
import com.nassiansoft.watchit.data.model.Movie
import io.reactivex.Completable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class DetailViewModelTest{

    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var repository: MovieRepositoryInterface
    private lateinit var viewModel: DetailViewModel

    @Before
    fun init(){
        repository=mock(MovieRepositoryInterface::class.java)
        viewModel=DetailViewModel(repository,PlayerHelper)
    }

    @Test
    fun testDeleteMovie_whenDeletingIsSuccessful(){

        //given
        val movie=Movie("1","jafar")
        viewModel.movie=movie
        `when`(repository.deleteMovie(movie))
            .thenReturn(Completable.complete())

        //when
        viewModel.deleteMovie()

        //then
        verify(repository).deleteMovie(movie)
        val result=viewModel.getMovieDeleted()
        result.observeForever {  }
        assertThat(result.value,`is`(true))

    }


    object PlayerHelper:IPlayerHelper{
        override fun preparePlayer(url: String?, playerView: PlayerView) {
        }

        override fun releasePlayer() {
        }

    }

}