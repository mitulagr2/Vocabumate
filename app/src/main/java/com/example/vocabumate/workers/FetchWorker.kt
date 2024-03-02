package com.example.vocabumate.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.vocabumate.KEY_API_SERVICE
import com.example.vocabumate.KEY_QUERY_OUTPUT
import com.example.vocabumate.KEY_WORD_QUERY
import com.example.vocabumate.R
import com.example.vocabumate.data.network.WordApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "FetchWorker"

class FetchWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
  override suspend fun doWork(): Result {
    val wordApiService = (inputData.keyValueMap[KEY_API_SERVICE] as WordApiService)
    val wordQuery = inputData.getString(KEY_WORD_QUERY)

    makeStatusNotification(
      "fetching_word",
      applicationContext
    )

    return withContext(Dispatchers.IO) {
      return@withContext try {
        require(!wordQuery.isNullOrBlank()) {
          val errorMessage =
            "invalid_word_query"
          Log.e(TAG, errorMessage)
          errorMessage
        }

        val output = wordApiService.getDefinition(wordQuery)
        val outputData = workDataOf(KEY_QUERY_OUTPUT to output)
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
