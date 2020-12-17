package com.nassiansoft.watchit

class TestApplication: BaseApp() {


    override fun getInjector()=DaggerTestComponent.builder()
        .getApp(this).build()
}