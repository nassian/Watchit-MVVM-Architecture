package com.nassiansoft.watchit.di

import com.nassiansoft.watchit.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment():HomeFragment

}