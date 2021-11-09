package com.fastwork.toefl.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.fastwork.toefl.data.local.database.dao.ParagraphDao
import com.fastwork.toefl.data.local.database.dao.ProductDao
import com.fastwork.toefl.data.local.database.dao.ReadingDao
import com.fastwork.toefl.data.local.database.dao.StructureDao
import com.fastwork.toefl.data.local.model.ParagraphReading
import com.fastwork.toefl.data.local.model.Product
import com.fastwork.toefl.data.local.model.Reading
import com.fastwork.toefl.data.local.model.Structure
import com.fastwork.toefl.utils.DATABASE_NAME
import com.fastwork.toefl.utils.PARAGRAPH_FILE_DATA_NAME
import com.fastwork.toefl.utils.READING_FILE_DATA_NAME
import com.fastwork.toefl.utils.STRUCTURE_FILE_DATA_NAME
import com.fastwork.toefl.workers.ParagraphSeedWorker
import com.fastwork.toefl.workers.ReadingSeedWorker
import com.fastwork.toefl.workers.StructureSeedWorker

@Database(
    entities = [Product::class, ParagraphReading::class, Reading::class, Structure::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun paragraphDao(): ParagraphDao
    abstract fun readingDao(): ReadingDao
    abstract fun structureDao(): StructureDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val listRequest = mutableListOf<WorkRequest>()
                            val requestSeedParagraph =
                                OneTimeWorkRequestBuilder<ParagraphSeedWorker>()
                                    .setInputData(workDataOf(ParagraphSeedWorker.KEY_FILENAME to PARAGRAPH_FILE_DATA_NAME))
                                    .build()
                            val requestSeedReading = OneTimeWorkRequestBuilder<ReadingSeedWorker>()
                                .setInputData(workDataOf(ReadingSeedWorker.KEY_FILENAME to READING_FILE_DATA_NAME))
                                .build()
                            val requestSeedStucture =
                                OneTimeWorkRequestBuilder<StructureSeedWorker>()
                                    .setInputData(workDataOf(StructureSeedWorker.KEY_FILENAME to STRUCTURE_FILE_DATA_NAME))
                                    .build()
                            listRequest.add(requestSeedParagraph)
                            listRequest.add(requestSeedReading)
                            listRequest.add(requestSeedStucture)
                            WorkManager.getInstance(context).enqueue(listRequest)
                        }
                    }
                )
                .build()
        }
    }

}