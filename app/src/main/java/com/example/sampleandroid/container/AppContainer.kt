package com.example.sampleandroid.container

import com.example.sampleandroid.activity.MainActivity
import com.example.sampleandroid.controller.SearchController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppContainerModule::class])
interface AppContainer {
    val searchController: SearchController
    fun inject(activity: MainActivity)
}
