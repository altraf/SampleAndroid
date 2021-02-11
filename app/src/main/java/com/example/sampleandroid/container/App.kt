package com.example.sampleandroid.container

import android.app.Application

class App : Application() {

    val container: AppContainer by lazy {
        DaggerAppContainer
            .builder()
            .appContainerModule(AppContainerModule(this))
            .build()
    }
}
