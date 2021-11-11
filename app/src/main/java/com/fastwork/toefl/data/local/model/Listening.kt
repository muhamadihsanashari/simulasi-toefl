package com.fastwork.toefl.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listening")
data class Listening(
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "option_answer")
    val optionAnswer: List<String>,
    @ColumnInfo(name = "answer")
    val answer: Int,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "audio_file_name")
    val audioFileName: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
