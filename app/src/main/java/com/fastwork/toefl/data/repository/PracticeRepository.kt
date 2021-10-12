package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.model.ParagraphAndReading

interface PracticeRepository {
    suspend fun getReadingData(difficulty: String): List<ParagraphAndReading>
}