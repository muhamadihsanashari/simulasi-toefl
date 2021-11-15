package com.fastwork.toefl.core

import android.app.Application
import androidx.work.Configuration
import com.fastwork.toefl.BuildConfig
import com.fastwork.toefl.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                databaseModule, networkModule, apiModule, repositoryModule, viewModelModule,
                appModule
            )
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
            .build()

}