package com.nassiansoft.watchit.di

import com.nassiansoft.watchit.ui.detail.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailModule {

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment():DetailFragment
}