package com.sehatq.test.injection

import com.sehatq.test.data.local.database.dao.ProductDao
import com.sehatq.test.data.remote.network.HomeAPI
import com.sehatq.test.data.repository.HomeRepositoryImpl
import com.sehatq.test.data.repository.ProductRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun providehomeRepositoryModule(api: HomeAPI): HomeRepositoryImpl {
        return HomeRepositoryImpl(api)
    }

    fun provideProductRepositoryModule(productDao: ProductDao): ProductRepositoryImpl {
        return ProductRepositoryImpl(productDao)
    }

    single {
        providehomeRepositoryModule(get())
    }

    single {
        provideProductRepositoryModule(get())
    }

}