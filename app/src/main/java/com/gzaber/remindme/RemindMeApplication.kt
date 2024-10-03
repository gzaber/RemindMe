package com.gzaber.remindme

import android.app.Application
import com.gzaber.remindme.di.dataModule
import com.gzaber.remindme.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RemindMeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RemindMeApplication)
            modules(dataModule, viewModelModule)
        }
    }
}