package com.rinfinity.powerplay.application

import android.app.Application
import androidx.room.Room
import com.rinfinity.powerplay.room.databse.DrawingDatabase
import com.rinfinity.powerplay.utils.CONST_APP_BASE_URL
import com.rinfinity.powerplay.utils.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var mApplication: PowerPlayApplication
const val CONST_DRAWING_DATABASE_NAME = "drawing-database"

class PowerPlayApplication : Application() {

    companion object {
        val Instance: PowerPlayApplication
            get() = mApplication
    }

    private lateinit var mNetworkService: NetworkService
    val networkService: NetworkService
        get() = mNetworkService

    private lateinit var mDrawingDatabase: DrawingDatabase
    val drawingDatabase: DrawingDatabase
        get() = mDrawingDatabase

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        mNetworkService = Retrofit.Builder().baseUrl(CONST_APP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NetworkService::class.java)
        mDrawingDatabase = Room.databaseBuilder(this, DrawingDatabase::class.java,
           CONST_DRAWING_DATABASE_NAME).build()
    }
}