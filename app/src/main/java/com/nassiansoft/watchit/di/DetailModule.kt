package com.nassiansoft.watchit.di

import android.app.Application
import com.google.android.exoplayer2.SimpleExoPlayer
import com.nassiansoft.watchit.IPlayerHelper
import com.nassiansoft.watchit.PlayerHelper
import com.nassiansoft.watchit.ui.detail.DetailFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailModule {


    @ContributesAndroidInjector()
    abstract fun contributeDetailFragment():DetailFragment

    @Binds
    abstract fun bindPlayerHelper(playerHelper: PlayerHelper):IPlayerHelper

}