package com.nalandya.base.data.remote.network

import com.nalandya.base.data.remote.response.ResponseHome
import retrofit2.Response
import retrofit2.http.GET

interface HomeAPI {
    @GET("https://private-4639ce-ecommerce56.apiary-mock.com/home")
    suspend fun getHomeData(): Response<List<ResponseHome>>
}