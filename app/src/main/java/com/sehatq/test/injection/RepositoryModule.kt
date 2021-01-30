package com.sehatq.test.injection

import com.sehatq.test.data.remote.network.HomeAPI
import com.sehatq.test.data.repository.HomeRepository
import com.sehatq.test.data.repository.HomeRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun providehomeRepositoryModule(api: HomeAPI): HomeRepositoryImpl {
        return HomeRepositoryImpl(api)
    }

    single {
        providehomeRepositoryModule(get())
    }

}