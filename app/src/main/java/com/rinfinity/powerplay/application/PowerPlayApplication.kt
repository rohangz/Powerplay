package com.rinfinity.powerplay.application

import android.app.Application
import com.rinfinity.powerplay.utils.CONST_APP_BASE_URL
import com.rinfinity.powerplay.utils.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var mApplication: PowerPlayApplication

class PowerPlayApplication : Application() {

    companion object {
        val Instance: PowerPlayApplication
            get() = mApplication
    }

    private lateinit var mNetworkService: NetworkService
    val networkService: NetworkService
        get() = mNetworkService

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        mNetworkService = Retrofit.Builder().baseUrl(CONST_APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NetworkService::class.java)
    }
}