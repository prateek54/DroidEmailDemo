package com.droidiot.demoProject.data.network

import com.droidiot.demoProject.data.model.EmailModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiHelper {

    @GET("api/getEmailData")
    fun getEmailList(): Single<List<EmailModel>>

}