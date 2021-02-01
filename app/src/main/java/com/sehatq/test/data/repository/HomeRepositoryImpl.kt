package com.sehatq.test.data.repository

import android.content.Context
import com.sehatq.test.data.remote.network.HomeAPI
import com.sehatq.test.data.remote.response.ResponseHome
import com.sehatq.test.utils.AppResult
import com.sehatq.test.utils.NetworkManager.isOnline
import com.sehatq.test.utils.Utils.handleApiError
import com.sehatq.test.utils.Utils.handleSuccess
import com.sehatq.test.utils.noNetworkConnectivityError

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