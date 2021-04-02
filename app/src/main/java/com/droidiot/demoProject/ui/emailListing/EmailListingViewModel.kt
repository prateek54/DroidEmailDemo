package com.droidiot.demoProject.ui.emailListing

import androidx.lifecycle.ViewModel
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.data.model.EmailModel
import com.droidiot.demoProject.utils.ScheduleProvider
import com.droidiot.demoProject.utils.liveData.MutableStateLiveData
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class EmailListingViewModel(
    val dataManager: DataManager,
    val disposable: CompositeDisposable,
    val scheduleProvider: ScheduleProvider
) : ViewModel() {
    private var _responseListData: MutableStateLiveData<List<EmailModel>> = MutableStateLiveData()
    val responseListData = _responseListData
    private var isDataLoaded = false

    init {
        //        getEmailList()
    }
    fun getEmailList() {
        if (!isDataLoaded) {
            isDataLoaded=true
            responseListData.postLoading()
            disposable.add(
                dataManager.getEmailList()
                    .observeOn(scheduleProvider.ui())
                    .delay(4, TimeUnit.SECONDS)
                    .subscribeOn(scheduleProvider.io())
                    .subscribe({ listResponse ->
                        responseListData.postSuccess(listResponse)
                    }, { errorThrowable ->
                        responseListData.postError(errorThrowable.message ?: "Something Went Wrong")
                    })
            )
        }

    }


    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) disposable.clear()
    }
}