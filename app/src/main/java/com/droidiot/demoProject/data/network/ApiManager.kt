package com.droidiot.demoProject.data.network

import com.droidiot.demoProject.data.model.EmailModel
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class ApiManager @Inject constructor() : ApiHelper {
    //inject the retrofit manager here
    @Inject
    lateinit var retrofitClient: Retrofit

    val apiHelper:ApiHelper by lazy {
        retrofitClient.create(ApiHelper::class.java)
    }

    override fun getEmailList(): Single<List<EmailModel>> {
        return apiHelper.getEmailList()
    }

}