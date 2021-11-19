package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.model.Score

interface ScoreRepository {
    suspend fun insertScore(data: Score): Int
    suspend fun getAllScores(userId: Int, category: String): List<Score>
    suspend fun getAllUserScores(category: String): List<Score>
    suspend fun resetScore(category: String): Int
}