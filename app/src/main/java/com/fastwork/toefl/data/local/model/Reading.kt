package com.fastwork.toefl.data.local.model

import androidx.room.*

@Entity(
    tableName = "reading",
    foreignKeys = [ForeignKey(
        entity = ParagraphReading::class,
        parentColumns = ["id"],
        childColumns = ["paragraph_id"]
    )],
    indices = [Index("paragraph_id")]
)
data class Reading(
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "option_answer")
    val optionAnswer: List<String>,
    @ColumnInfo(name = "paragraph_id")
    val paragraphId: Int,
    @ColumnInfo(name = "paragraph")
    val paragraph: String,
    @ColumnInfo(name = "answer")
    val answer: Int,
    @ColumnInfo(name = "difficulty")
    val difficulty: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
