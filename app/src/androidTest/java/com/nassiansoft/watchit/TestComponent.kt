package com.nassiansoft.watchit

import android.app.Application
import com.nassiansoft.watchit.data.FakeDataModule
import com.nassiansoft.watchit.di.ActivityModule
import com.nassiansoft.watchit.di.AppComponent
import com.nassiansoft.watchit.di.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class
    ,ActivityModule::class
    , FakeDataModule::class])
interface TestComponent:AppComponent{

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun getApp(application: Application):Builder

        fun build():TestComponent
    }
}