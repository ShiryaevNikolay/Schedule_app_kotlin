package com.shiryaev.data

import android.app.Application
import com.shiryaev.data.di.AppComponent
import com.shiryaev.data.di.DaggerAppComponent
import com.shiryaev.data.di.modules.AppModule

class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()

        sAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    companion object {
        private lateinit var sAppComponent: AppComponent

        fun getAppComponent() = sAppComponent
    }
}