package com.elkir.scanner

import android.app.Application
import com.elkir.scanner.di.AppComponent
import com.elkir.scanner.di.AppModule
import com.elkir.scanner.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
           // .appModule(AppModule(this))
            .build()

        appComponent.inject(this)
    }


    companion object {
        lateinit var appComponent: AppComponent
    }
}