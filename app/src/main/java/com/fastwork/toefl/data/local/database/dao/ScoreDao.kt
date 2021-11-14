package com.fastwork.toefl.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.fastwork.toefl.data.local.model.Score

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score WHERE user_id=:userId AND category=:category")
    suspend fun getScores(userId: Int, category: String): List<Score>
}