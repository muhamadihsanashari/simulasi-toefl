package com.fastwork.toefl.di

import com.fastwork.toefl.data.local.database.AppDatabase
import com.fastwork.toefl.data.local.database.dao.*
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

    fun provideListeningDao(database: AppDatabase): ListeningDao {
        return database.listeningDao()
    }

    fun provideScoreDao(database: AppDatabase): ScoreDao {
        return database.scoreDao()
    }

    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
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

    single { provideListeningDao(get()) }

    single { provideScoreDao(get()) }

    single { provideUserDao(get()) }

}