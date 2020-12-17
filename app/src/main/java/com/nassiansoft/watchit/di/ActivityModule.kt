package com.nassiansoft.watchit.di

import com.nassiansoft.watchit.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [HomeModule::class,SearchModule::class,DetailModule::class])
    abstract fun provideActivity():MainActivity

}