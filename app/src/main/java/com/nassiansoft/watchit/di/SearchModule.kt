package com.nassiansoft.watchit.di

import com.nassiansoft.watchit.ui.search.SearchFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchModule {

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment():SearchFragment


}