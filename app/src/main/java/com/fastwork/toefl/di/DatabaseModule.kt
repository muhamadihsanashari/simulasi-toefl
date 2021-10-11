package com.fastwork.toefl.di

import com.fastwork.toefl.data.local.database.AppDatabase
import com.fastwork.toefl.data.local.database.dao.ParagraphDao
import com.fastwork.toefl.data.local.database.dao.ProductDao
import com.fastwork.toefl.data.local.database.dao.ReadingDao
import com.fastwork.toefl.data.local.database.dao.StructureDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    fun provideParagraphReadingDao(database: AppDatabase): ParagraphDao {
        return database.paragraphDao()
    }

    fun provideReadingDao(database: AppDatabase): ReadingDao {
        return database.readingDao()
    }

    fun provideStuctureDao(database: AppDatabase): StructureDao {
        return database.structureDao()
    }


    single {
        AppDatabase.getInstance(androidContext())
    }

    single {
        provideProductDao(get())
    }

    single { provideParagraphReadingDao(get()) }

    single { provideReadingDao(get()) }

    single { provideStuctureDao(get()) }

}