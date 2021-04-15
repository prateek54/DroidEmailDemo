package com.droidiot.demoProject.ui.emailListing

import androidx.lifecycle.ViewModel
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.data.model.EmailModel
import com.droidiot.demoProject.utils.ScheduleProvider
import com.droidiot.demoProject.utils.liveData.MutableStateLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class EmailListingViewModel(
    val dataManager: DataManager,
    val disposable: CompositeDisposable,
    val scheduleProvider: ScheduleProvider
) : ViewModel() {
    private var _responseListData: MutableStateLiveData<List<EmailModel>> = MutableStateLiveData()
    val responseListData = _responseListData

    private var _filterListData: MutableStateLiveData<List<EmailModel>> = MutableStateLiveData()
    val filterListData = _filterListData

    private var isDataLoaded = false

    init {
        //        getEmailList()
    }

    fun getEmailList() {
        if (!isDataLoaded) {
            isDataLoaded = true
            responseListData.postLoading()
            disposable.add(
                dataManager.getEmailList()
                    .observeOn(scheduleProvider.ui())
                    .subscribeOn(scheduleProvider.io())
                    .subscribe({ listResponse ->
                        responseListData.postSuccess(listResponse)
                    }, { errorThrowable ->
                        responseListData.postError(errorThrowable.message ?: "Something Went Wrong")
                    })
            )
        }

    }

    fun getEmailListFiltered(search: String) {
        val orignalList = responseListData.value?.data ?: arrayListOf()
        if (search.length < 0) filterListData.postValue(responseListData.value)
        else {
            filterListData.postLoading()
            disposable.add(Observable.fromIterable(orignalList)
                .subscribeOn(scheduleProvider.io())
                .debounce(100, TimeUnit.MICROSECONDS)
                .filter {
                    val msg = it.message ?: ""
                    val subject = it.subject ?: ""
                    val fName = it.sender?.first_name ?: ""
                    val lName = it.sender?.last_name ?: ""
                    return@filter ("$msg $subject $fName $lName").contains(search, true)
                }
                .toList()
                .observeOn(scheduleProvider.ui())
                .subscribe({ filterList ->
                    filterListData.postSuccess(filterList)
                }, { error ->
                    filterListData.postError(error.message!!)
                })
            )
        }
    }


    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) disposable.clear()
    }
}