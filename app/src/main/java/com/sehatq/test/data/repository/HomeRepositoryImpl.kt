package com.sehatq.test.data.repository

import com.sehatq.test.data.remote.network.HomeAPI
import com.sehatq.test.data.remote.response.ResponseHome
import com.sehatq.test.utils.AppResult
import com.sehatq.test.utils.Utils.handleApiError
import com.sehatq.test.utils.Utils.handleSuccess

class HomeRepositoryImpl(
    private val homeAPI: HomeAPI
) : HomeRepository {

    override suspend fun getHome(): AppResult<ResponseHome> {
        val response = homeAPI.getHomeData()
        return try {
            if (response.isSuccessful) {
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }

}