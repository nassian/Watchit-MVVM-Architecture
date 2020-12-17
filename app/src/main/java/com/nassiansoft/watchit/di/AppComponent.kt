package com.nassiansoft.watchit.di

import android.app.Application
import com.nassiansoft.watchit.BaseApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class
    ,ActivityModule::class
    ,DataModule::class])
interface AppComponent:AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun getApp(application: Application):Builder

        fun build():AppComponent
    }

}