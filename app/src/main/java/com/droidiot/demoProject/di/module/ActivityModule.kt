package com.droidiot.demoProject.di.module

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.di.ActivityScope
import com.droidiot.demoProject.ui.emailListing.EmailListingActivity
import com.droidiot.demoProject.ui.emailListing.EmailListingViewModel
import com.droidiot.demoProject.ui.emailListing.EmailListingViewModelFactory
import com.droidiot.demoProject.utils.ScheduleProvider
import com.droidiot.demoProject.utils.ScheduleProviderImpl
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(var activity: Activity) {
    //here we will define all the things which we are gonna provide to our ActivityComponent class like

    @Provides
    @ActivityScope
    fun provideViewModel(dataManager: DataManager,disposable: CompositeDisposable,scheduleProvider: ScheduleProvider): EmailListingViewModel {
        return ViewModelProvider(
            (activity as EmailListingActivity),
            EmailListingViewModelFactory(
                dataManager,disposable,scheduleProvider
            )
        ).get(EmailListingViewModel::class.java)
    }

    @Provides
    fun provideScheduler():ScheduleProvider =
        ScheduleProviderImpl()

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

}