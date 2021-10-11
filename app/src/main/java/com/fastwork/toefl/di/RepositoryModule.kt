package com.fastwork.toefl.di

import android.content.Context
import com.fastwork.toefl.data.local.database.dao.ParagraphDao
import com.fastwork.toefl.data.local.database.dao.ProductDao
import com.fastwork.toefl.data.remote.network.HomeAPI
import com.fastwork.toefl.data.repository.HomeRepository
import com.fastwork.toefl.data.repository.HomeRepositoryImpl
import com.fastwork.toefl.data.repository.ProductRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun providehomeRepositoryModule(
        api: HomeAPI,
        context: Context,
        paragraphDao: ParagraphDao
    ): HomeRepositoryImpl {
        return HomeRepositoryImpl(api, context, paragraphDao)
    }

    fun provideProductRepositoryModule(productDao: ProductDao): ProductRepositoryImpl {
        return ProductRepositoryImpl(productDao)
    }

    single<HomeRepository> {
        HomeRepositoryImpl(get(), androidContext(), get())
    }

    single {
        provideProductRepositoryModule(get())
    }

}