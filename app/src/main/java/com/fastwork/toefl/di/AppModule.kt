package com.fastwork.toefl.di

import android.app.Application
import android.content.Context
import com.fastwork.toefl.R
import org.koin.dsl.module

val appModule = module {
    single {
        get<Application>().run {
            getSharedPreferences(
                getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
        }
    }
}