package com.fastwork.toefl.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "score")
data class Score(
    @ColumnInfo(name = "score")
    val score: Int,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "user_id")
    val userId: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}