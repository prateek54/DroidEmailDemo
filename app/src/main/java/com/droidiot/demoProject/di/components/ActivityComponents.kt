package com.droidiot.demoProject.di.components

import com.droidiot.demoProject.di.ActivityScope
import com.droidiot.demoProject.di.module.ActivityModule
import com.droidiot.demoProject.ui.emailListing.EmailListingActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponents::class])
interface ActivityComponents {
    //Here we define all the activity level things where we want injection also here have all the app module items already by default
    fun inject(activity: EmailListingActivity)

}