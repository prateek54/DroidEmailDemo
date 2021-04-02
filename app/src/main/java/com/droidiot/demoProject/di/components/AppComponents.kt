package com.droidiot.demoProject.di.components

import android.app.Application
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponents {
    //Here we define all the application level things where we want injection
    fun inject(application: Application)

    fun getDataManager(): DataManager


}