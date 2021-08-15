package com.nalandya.base.di

import android.content.Context
import com.nalandya.base.data.local.database.dao.ProductDao
import com.nalandya.base.data.remote.network.HomeAPI
import com.nalandya.base.data.repository.HomeRepositoryImpl
import com.nalandya.base.data.repository.ProductRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun providehomeRepositoryModule(api: HomeAPI, context: Context): HomeRepositoryImpl {
        return HomeRepositoryImpl(api, context)
    }

    fun provideProductRepositoryModule(productDao: ProductDao): ProductRepositoryImpl {
        return ProductRepositoryImpl(productDao)
    }

    single {
        HomeRepositoryImpl(get(), androidContext())
    }

    single {
        provideProductRepositoryModule(get())
    }

}