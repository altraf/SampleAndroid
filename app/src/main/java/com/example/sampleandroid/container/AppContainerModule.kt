package com.example.sampleandroid.container

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContainerModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApp() = app
}
