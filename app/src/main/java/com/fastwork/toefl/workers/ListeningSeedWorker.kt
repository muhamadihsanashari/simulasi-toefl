package com.fastwork.toefl.workers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.fastwork.toefl.data.local.database.AppDatabase
import com.fastwork.toefl.data.local.model.Listening
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("LogNotTimber")
class ListeningSeedWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val listeningType = object : TypeToken<List<Listening>>() {}.type
                        val listeningList: List<Listening> =
                            Gson().fromJson(jsonReader, listeningType)

                        val database = AppDatabase.getInstance(applicationContext)
                        database.listeningDao().insertAll(listeningList)

                        Result.success()
                    }
                }
            } else {
                Log.e(ParagraphSeedWorker.TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(ParagraphSeedWorker.TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        const val TAG = "ListeningSeedWorker"
        const val KEY_FILENAME = "LISTENING_FILE_NAME"
    }
}