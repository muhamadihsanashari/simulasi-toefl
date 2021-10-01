package com.fastwork.toefl.data.repository

import android.content.Context
import com.fastwork.toefl.data.remote.network.HomeAPI
import com.fastwork.toefl.data.remote.response.ResponseHome
import com.fastwork.toefl.utils.AppResult
import com.fastwork.toefl.utils.NetworkManager.isOnline
import com.fastwork.toefl.utils.Utils.handleApiError
import com.fastwork.toefl.utils.Utils.handleSuccess
import com.fastwork.toefl.utils.noNetworkConnectivityError

class HomeRepositoryImpl(
    private val homeAPI: HomeAPI, private val context: Context
) : HomeRepository {

    override suspend fun getHome(): AppResult<List<ResponseHome>> {
        return if (isOnline(context)) {
            val response = homeAPI.getHomeData()
            try {
                if (response.isSuccessful) {
                    handleSuccess(response)
                } else {
                    handleApiError(response)
                }
            } catch (e: Exception) {
                AppResult.Error(e)
            }
        } else {
            context.noNetworkConnectivityError()
        }
    }

}