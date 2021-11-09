package com.fastwork.toefl.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fastwork.toefl.data.local.model.Structure

@Dao
interface StructureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Structure>)

    @Query("SELECT * FROM structure WHERE category=:category")
    fun getAllStructure(category: String): List<Structure>


}