package com.nalandya.base.data.repository

import android.content.Context
import com.nalandya.base.data.remote.network.HomeAPI
import com.nalandya.base.data.remote.response.ResponseHome
import com.nalandya.base.utils.AppResult
import com.nalandya.base.utils.NetworkManager.isOnline
import com.nalandya.base.utils.Utils.handleApiError
import com.nalandya.base.utils.Utils.handleSuccess
import com.nalandya.base.utils.noNetworkConnectivityError

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