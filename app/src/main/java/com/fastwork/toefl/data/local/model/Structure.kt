package com.fastwork.toefl.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "structure")
data class Structure(
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "option_answer")
    val optionAnswer: List<String>,
    @ColumnInfo(name = "answer")
    val answer: Int,
    @ColumnInfo(name = "category")
    val category: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
