package com.fastwork.toefl.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fastwork.toefl.data.local.model.ParagraphReading

@Dao
interface ParagraphDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<ParagraphReading>)

    @Query("SELECT * FROM paragraph")
    suspend fun getAll(): List<ParagraphReading>
}