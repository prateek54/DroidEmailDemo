package com.droidiot.demoProject.utils.liveData

import com.droidiot.demoProject.utils.Status


class BaseStateData<T>(
    var status: Status? = Status.CREATED,
    var data: T? = null,
    var errorMessage: String? = null
) {

    fun loading(): BaseStateData<T> {
        this.status = Status.LOADING
        return this
    }

    fun success(data: T): BaseStateData<T> {
        this.status = Status.SUCCESS
        this.data = data
        return this
    }

    fun error(errorMessage: String): BaseStateData<T> {
        this.status = Status.ERROR
        this.errorMessage = errorMessage
        return this
    }

}