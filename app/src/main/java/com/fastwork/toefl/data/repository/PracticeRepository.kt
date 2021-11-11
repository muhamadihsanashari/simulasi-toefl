package com.fastwork.toefl.data.repository

import com.fastwork.toefl.data.local.model.Listening
import com.fastwork.toefl.data.local.model.Reading
import com.fastwork.toefl.data.local.model.Structure

interface PracticeRepository {
    suspend fun getReadingData(difficulty: String): List<Reading>
    suspend fun getStructureData(category: String): List<Structure>
    suspend fun getListeningData(category: String): List<Listening>
}