package com.sehatq.test.data.repository

import com.sehatq.test.data.remote.response.ResponseHome
import com.sehatq.test.utils.AppResult

interface HomeRepository {
    suspend fun getHome() : AppResult<List<ResponseHome>>
}