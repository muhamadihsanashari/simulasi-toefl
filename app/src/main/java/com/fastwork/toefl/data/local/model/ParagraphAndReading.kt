package com.fastwork.toefl.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class ParagraphAndReading(
    @Embedded
    val paragraphReading: ParagraphReading,

    @Relation(parentColumn = "id", entityColumn = "paragraph_id")
    val reading: List<Reading> = emptyList()

)
