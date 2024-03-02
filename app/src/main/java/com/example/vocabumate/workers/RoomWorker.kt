package com.example.vocabumate.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.vocabumate.KEY_ACTION
import com.example.vocabumate.KEY_OUTPUT_DATA
import com.example.vocabumate.KEY_OUTPUT_TYPE
import com.example.vocabumate.KEY_PAYLOAD
import com.example.vocabumate.data.Word
import com.example.vocabumate.data.local.VocabumateDatabase
import com.example.vocabumate.data.local.WordDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TAG = "RoomWorker"

class RoomWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
  private val wordDao: WordDao = VocabumateDatabase.getDatabase(applicationContext).wordDao()

  private suspend fun handleRoomAction(type: String, payload: String): Word? {
    return when (type) {
      "GET" ->
        wordDao.getWord(payload)

      "INSERT" -> {
        val word = Json.decodeFromString<Word>(payload)
        wordDao.insert(word)
        word
      }

      "DELETE" -> {
        val word = Json.decodeFromString<Word>(payload)
        wordDao.delete(word)
        word
      }

      else -> null
    }
  }

  override suspend fun doWork(): Result {
    val payload = inputData.getString(KEY_PAYLOAD) ?: ""
    val action = inputData.getString(KEY_ACTION) ?: ""

    makeStatusNotification(
      "accessing_room",
      applicationContext
    )

    return withContext(Dispatchers.IO) {
      return@withContext try {

        val output = handleRoomAction(action, payload)

        if (output !== null) {
          val outputData =
            workDataOf(KEY_OUTPUT_DATA to Json.encodeToString(output), KEY_OUTPUT_TYPE to "LOCAL")
          Result.failure(outputData)
        } else {
          // Continue to fetch worker
          Result.success()
        }
      } catch (throwable: Throwable) {
        Log.e(
          TAG,
          "error_accessing_room",
          throwable
        )
        // Continue to fetch worker
        Result.success()
      }
    }
  }
}