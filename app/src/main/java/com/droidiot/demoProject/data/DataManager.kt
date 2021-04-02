package com.droidiot.demoProject.data

import com.droidiot.demoProject.data.model.EmailModel
import com.droidiot.demoProject.data.network.ApiHelper
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

class DataManager @Inject constructor(val apiHelper: ApiHelper) : ApiHelper {

    override fun getEmailList(): Single<List<EmailModel>> {
        return apiHelper.getEmailList()
    }



}