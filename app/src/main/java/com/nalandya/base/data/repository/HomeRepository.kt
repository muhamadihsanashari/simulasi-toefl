package com.nalandya.base.data.repository

import com.nalandya.base.data.remote.response.ResponseHome
import com.nalandya.base.utils.AppResult

interface HomeRepository {
    suspend fun getHome() : AppResult<List<ResponseHome>>
}