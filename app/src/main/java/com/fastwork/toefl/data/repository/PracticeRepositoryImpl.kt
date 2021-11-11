package com.fastwork.toefl.data.repository


import com.fastwork.toefl.data.local.database.dao.ListeningDao
import com.fastwork.toefl.data.local.database.dao.ReadingDao
import com.fastwork.toefl.data.local.database.dao.StructureDao
import com.fastwork.toefl.data.local.model.Listening
import com.fastwork.toefl.data.local.model.Reading
import com.fastwork.toefl.data.local.model.Structure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PracticeRepositoryImpl(
    private val readingDao: ReadingDao,
    private val structureDao: StructureDao,
    private val listeningDao: ListeningDao
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

    override suspend fun getStructureData(category: String): List<Structure> {
        var result = listOf<Structure>()
        withContext(Dispatchers.IO) {
            try {
                result = structureDao.getAllStructure(category)
            } catch (e: Exception) {
                Timber.e(e.message.toString())
            }
        }
        return result
    }

    override suspend fun getListeningData(category: String): List<Listening> {
        var result = listOf<Listening>()
        withContext(Dispatchers.IO) {
            try {
                result = listeningDao.getAllListening(category)
            } catch (e: java.lang.Exception) {
                Timber.e(e.message.toString())
            }
        }
        return result
    }
}