package com.fastwork.toefl.data.repository

import android.content.Context
import com.fastwork.toefl.data.local.database.dao.ParagraphDao
import com.fastwork.toefl.data.local.model.ParagraphReading
import com.fastwork.toefl.data.remote.network.HomeAPI
import com.fastwork.toefl.data.remote.response.ResponseHome
import com.fastwork.toefl.utils.AppResult
import com.fastwork.toefl.utils.NetworkManager.isOnline
import com.fastwork.toefl.utils.Utils.handleApiError
import com.fastwork.toefl.utils.Utils.handleSuccess
import com.fastwork.toefl.utils.noNetworkConnectivityError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class HomeRepositoryImpl(
    private val homeAPI: HomeAPI,
    private val context: Context,
    private val paragraphDao: ParagraphDao
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

    override suspend fun getListParagraph(): List<ParagraphReading> {
        var result = listOf<ParagraphReading>()
        withContext(Dispatchers.IO) {
            try {
                result = paragraphDao.getAll()
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }

        return result
    }

}