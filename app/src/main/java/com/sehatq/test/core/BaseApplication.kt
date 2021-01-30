package com.sehatq.test.core

import android.app.Application
import com.sehatq.test.injection.databaseModule
import com.sehatq.test.injection.networkModule
import com.sehatq.test.injection.repositoryModule
import com.sehatq.test.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(databaseModule, networkModule, repositoryModule, viewModelModule)
        }
    }
}