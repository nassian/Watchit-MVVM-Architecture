package com.nassiansoft.watchit.di

import android.app.Application
import androidx.room.Room
import com.nassiansoft.watchit.data.MovieRepository
import com.nassiansoft.watchit.data.MovieRepositoryInterface
import com.nassiansoft.watchit.data.local.LocalDataSource
import com.nassiansoft.watchit.data.local.MovieDataBase
import com.nassiansoft.watchit.data.network.Api
import com.nassiansoft.watchit.data.network.RemoteDataSource
import com.nassiansoft.watchit.data.network.RemoteDataSourceInterface
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class DataModule {

    companion object {

        @Singleton
        @Provides
        fun provideMovieDatabase(application: Application): MovieDataBase =
            Room.databaseBuilder(application, MovieDataBase::class.java, "movie.db")
                .build()

        @Singleton
        @Provides
        fun provideRetrofitClient(): Api {
            val baseUrl = "https://itunes.apple.com/"
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build().create(Api::class.java)
        }

        @Singleton
        @Provides
        fun provideRepository(
            localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource
        ): MovieRepository = MovieRepository(localDataSource, remoteDataSource)


    }

    @Binds
    abstract fun bindRepository(repository: MovieRepository):MovieRepositoryInterface

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSource):
            RemoteDataSourceInterface


}