package com.sehatq.test.core

import android.app.Application
import com.sehatq.test.injection.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(databaseModule, networkModule, apiModule, repositoryModule, viewModelModule)
        }
    }
}