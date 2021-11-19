package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.database.dao.ScoreDao
import com.fastwork.toefl.data.local.model.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ScoreRepositoryImpl(private val scoreDao: ScoreDao) : ScoreRepository {

    override suspend fun insertScore(data: Score): Int {
        var result = 0
        withContext(Dispatchers.IO) {
            try {
                result = scoreDao.inserScore(data).toInt()
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
        return result
    }

    override suspend fun getAllScores(userId: Int, category: String): List<Score> {
        val result = mutableListOf<Score>()
        withContext(Dispatchers.IO) {
            try {
                result.addAll(scoreDao.getScores(category = category, userId = userId))
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
        return result
    }

    override suspend fun getAllUserScores(category: String): List<Score> {
        val result = mutableListOf<Score>()
        withContext(Dispatchers.IO) {
            try {
                result.addAll(scoreDao.getAllUserScores(category = category))
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
        return result
    }

    override suspend fun resetScore(category: String): Int {
        var result = 0
        withContext(Dispatchers.IO) {
            try {
                result = scoreDao.resetScore(category)
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
        return result
    }

}