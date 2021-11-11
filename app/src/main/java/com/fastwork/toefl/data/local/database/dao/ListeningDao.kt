package com.fastwork.toefl.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fastwork.toefl.data.local.model.Listening

@Dao
interface ListeningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Listening>)

    @Query("SELECT * FROM listening WHERE category=:category")
    fun getAllListening(category: String): List<Listening>
}