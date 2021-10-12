package com.fastwork.toefl.data.repository


import com.fastwork.toefl.data.local.database.dao.ReadingDao
import com.fastwork.toefl.data.local.database.dao.StructureDao
import com.fastwork.toefl.data.local.model.ParagraphAndReading
import com.fastwork.toefl.data.local.model.Reading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PracticeRepositoryImpl(
    private val readingDao: ReadingDao,
    private val structureDao: StructureDao
) : PracticeRepository {


    override suspend fun getReadingData(difficulty: String): List<Reading> {
        var result = listOf<Reading>()
        withContext(Dispatchers.IO) {
            try {
                result = readingDao.getAllReading(difficulty)
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
        return result
    }


}