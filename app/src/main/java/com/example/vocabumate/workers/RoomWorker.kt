package com.example.vocabumate.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.vocabumate.KEY_ACTION_PAYLOAD
import com.example.vocabumate.KEY_ACTION_TYPE
import com.example.vocabumate.KEY_LOCAL_OUTPUT
import com.example.vocabumate.KEY_QUERY_OUTPUT
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
    val roomAction = inputData.getString(KEY_ACTION_TYPE)
    val payload = inputData.getString(KEY_ACTION_PAYLOAD)

    makeStatusNotification(
      "fetching_word",
      applicationContext
    )

    return withContext(Dispatchers.IO) {
      return@withContext try {
        require(!roomAction.isNullOrBlank() && !payload.isNullOrBlank()) {
          val errorMessage =
            "invalid_action_or_payload"
          Log.e(TAG, errorMessage)
          errorMessage
        }

        val output = handleRoomAction(roomAction, payload)
        val outputJson = if (output === null) "" else Json.encodeToString(output)
        Log.d("Test10", outputJson)
        val outputData = workDataOf(KEY_LOCAL_OUTPUT to outputJson)
        Result.success(outputData)
      } catch (throwable: Throwable) {
        Log.e(
          TAG,
          "error_fetching_word",
          throwable
        )
        Result.failure()
      }
    }
  }
}