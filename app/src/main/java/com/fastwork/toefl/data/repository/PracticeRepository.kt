package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.model.Reading

interface PracticeRepository {
    suspend fun getReadingData(difficulty: String): List<Reading>
}