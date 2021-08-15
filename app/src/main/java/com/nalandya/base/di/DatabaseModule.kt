package com.nalandya.base.di

import android.app.Application
import androidx.room.Room
import com.nalandya.base.data.local.database.AppDatabase
import com.nalandya.base.data.local.database.dao.ProductDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "sehatq")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    single {
        provideDatabase(androidApplication())
    }

    single {
        provideProductDao(get())
    }

}