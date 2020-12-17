package com.nassiansoft.watchit.data

import android.app.Application
import androidx.room.Room
import com.nassiansoft.watchit.data.local.LocalDataSource
import com.nassiansoft.watchit.data.local.MovieDataBase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class FakeDataModule {

    companion object {

        @Singleton
        @Provides
        fun provideMovieDatabase(application: Application): MovieDataBase =
            Room.inMemoryDatabaseBuilder(application, MovieDataBase::class.java)
                .allowMainThreadQueries()
                .build()


        @Singleton
        @Provides
        fun provideRemoteDataSource()=FakeRemoteDataSource()

        @Singleton
        @Provides
        fun provideRepository(
            localDataSource: LocalDataSource, remoteDataSource: FakeRemoteDataSource
        ): MovieRepository = MovieRepository(localDataSource, remoteDataSource)


    }

    @Binds
    abstract fun bindRepository(repository: MovieRepository):MovieRepositoryInterface




}