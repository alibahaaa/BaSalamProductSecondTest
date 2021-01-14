package com.basalam.basalamproduct

import android.app.Application
import com.basalam.basalamproduct.di.AppComponent
import com.basalam.basalamproduct.di.DaggerAppComponent

class AppController : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .factory()
            .create(this,5)
    }

    fun getComponent() = appComponent
}