package com.droidiot.demoProject.ui.emailListing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.utils.ScheduleProvider
import io.reactivex.disposables.CompositeDisposable

class EmailListingViewModelFactory(
    private val dataManager: DataManager,
    private val disposable: CompositeDisposable,
   private val scheduleProvider: ScheduleProvider
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmailListingViewModel(
            dataManager,disposable,scheduleProvider
        ) as T
    }
}