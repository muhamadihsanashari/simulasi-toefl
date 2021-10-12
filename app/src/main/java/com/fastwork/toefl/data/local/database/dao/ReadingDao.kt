package com.fastwork.toefl.data.local.database.dao

import androidx.room.*
import com.fastwork.toefl.data.local.model.ParagraphAndReading
import com.fastwork.toefl.data.local.model.Reading

@Dao
interface ReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Reading>)

    @Query("SELECT * FROM reading WHERE difficulty=:difficulty")
    fun getAllReading(difficulty: String): List<Reading>
}