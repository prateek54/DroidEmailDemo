package com.droidiot.demoProject.utils.liveData

import androidx.lifecycle.MutableLiveData


class MutableStateLiveData<T> : MutableLiveData<BaseStateData<T>>() {


    fun postLoading() {
        postValue(BaseStateData<T>().loading())
    }

    fun postError(errorMessage: String) {
        postValue(BaseStateData<T>().error(errorMessage))
    }

    fun postSuccess(data: T) {
        postValue(BaseStateData<T>().success(data))
    }

}