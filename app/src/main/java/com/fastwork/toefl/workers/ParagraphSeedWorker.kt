package com.fastwork.toefl.workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fastwork.toefl.data.local.database.AppDatabase
import com.fastwork.toefl.data.local.model.ParagraphReading
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("LogNotTimber")
class ParagraphSeedWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val paragraphType = object : TypeToken<List<ParagraphReading>>() {}.type
                        val paragraphList: List<ParagraphReading> =
                            Gson().fromJson(jsonReader, paragraphType)

                        val database = AppDatabase.getInstance(applicationContext)
                        database.paragraphDao().insertAll(paragraphList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        const val TAG = "ParagraphSeedWorker"
        const val KEY_FILENAME = "PARAGRAPH_FILE_NAME"
    }

}