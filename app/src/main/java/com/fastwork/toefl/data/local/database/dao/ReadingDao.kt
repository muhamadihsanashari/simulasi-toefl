package com.fastwork.toefl.data.local.database.dao

import androidx.room.*
import com.fastwork.toefl.data.local.model.ParagraphAndReading
import com.fastwork.toefl.data.local.model.ParagraphReading
import com.fastwork.toefl.data.local.model.Reading

@Dao
interface ReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Reading>)

    @Transaction
    @Query("SELECT * FROM paragraph WHERE id in (SELECT DISTINCT (paragraph_id) FROM reading WHERE difficulty=:difficulty)")
    fun getAllReading(difficulty: String): List<ParagraphAndReading>
}