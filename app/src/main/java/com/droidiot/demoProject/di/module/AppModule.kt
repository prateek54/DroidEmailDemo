package com.droidiot.demoProject.di.module

import android.app.Application
import com.droidiot.demoProject.data.DataManager
import com.droidiot.demoProject.data.network.ApiHelper
import com.droidiot.demoProject.data.network.ApiManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(var application: Application) {
    //here we will define all the things which we are gonna provide to our AppComponent class like

    @Provides
    @Singleton
    fun provideDataManager(apiHelper: ApiHelper) = DataManager(apiHelper)
    //here we will define all the items we need for injection and also will define them its components if it needed to be injected in any activity/module

    @Provides
    @Singleton
    fun provideApiManager(apiManager: ApiManager): ApiHelper = apiManager

    @Provides
    @Singleton
    fun provideRetrofitProvider(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl("https://605fa0b204b05d0017ba1959.mockapi.io/mock/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.interceptors().add(interceptor)
        return client.build()

    }

}