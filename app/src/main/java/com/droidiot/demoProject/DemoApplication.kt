package com.droidiot.demoProject

import android.app.Application
import com.droidiot.demoProject.di.components.AppComponents
import com.droidiot.demoProject.di.components.DaggerAppComponents
import com.droidiot.demoProject.di.module.AppModule


class DemoApplication : Application() {
    private lateinit var appComponents: AppComponents

    override fun onCreate() {
        super.onCreate()
        appComponents = DaggerAppComponents.builder().appModule(AppModule(this)).build()
        appComponents.inject(this)
    }

    fun getApplicationComponent() = appComponents
}
