package com.nassiansoft.watchit

import com.nassiansoft.watchit.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class BaseApp:DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return getInjector()
    }

    open fun getInjector()=DaggerAppComponent.builder()
        .getApp(this).build()
}