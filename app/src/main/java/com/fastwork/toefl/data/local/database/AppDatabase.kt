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
import com.fastwork.toefl.data.local.database.dao.*
import com.fastwork.toefl.data.local.model.*
import com.fastwork.toefl.utils.*
import com.fastwork.toefl.workers.*

@Database(
    entities = [Product::class, ParagraphReading::class, Reading::class, Structure::class, Listening::class, Score::class, User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun paragraphDao(): ParagraphDao
    abstract fun readingDao(): ReadingDao
    abstract fun structureDao(): StructureDao
    abstract fun listeningDao(): ListeningDao
    abstract fun scoreDao(): ScoreDao
    abstract fun userDao(): UserDao


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
                            val requestSeedListening =
                                OneTimeWorkRequestBuilder<ListeningSeedWorker>()
                                    .setInputData(workDataOf(ListeningSeedWorker.KEY_FILENAME to LISTENING_FILE_DATA_NAME))
                                    .build()
                            val requestSeedUser = OneTimeWorkRequestBuilder<UserSeedWorker>()
                                .setInputData(workDataOf(UserSeedWorker.KEY_FILENAME to USER_FILE_DATA_NAME))
                                .build()
                            listRequest.add(requestSeedParagraph)
                            listRequest.add(requestSeedReading)
                            listRequest.add(requestSeedStucture)
                            listRequest.add(requestSeedListening)
                            listRequest.add(requestSeedUser)
                            WorkManager.getInstance(context).enqueue(listRequest)
                        }
                    }
                )
                .build()
        }
    }

}