package com.sehatq.test.data.remote.network

import com.sehatq.test.data.remote.response.ResponseHome
import com.sehatq.test.utils.AppResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface HomeAPI {
    @GET("https://private-4639ce-ecommerce56.apiary-mock.com/home")
    fun getHomeData(): Response<ResponseHome>
}