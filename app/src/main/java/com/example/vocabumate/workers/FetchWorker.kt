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
import com.example.vocabumate.data.network.WordApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private const val TAG = "FetchWorker"

class FetchWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

  private val baseUrl =
    "https://vocabumate.onrender.com"

  private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

  private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()

  private val retrofitService: WordApiService by lazy {
    retrofit.create(WordApiService::class.java)
  }

  private suspend fun handleFetchAction(type: String, payload: String): String {
    return when (type) {
      "DAILY" -> {
        val output = retrofitService.getDaily()
        makeStatusNotification(
          "Today's word of the day is: ${Json.decodeFromString<Word>(output).word}!",
          applicationContext
        )
        output
      }

      else -> retrofitService.getWord(payload)
    }
  }

  override suspend fun doWork(): Result {
    val payload = inputData.getString(KEY_PAYLOAD) ?: ""
    val action = inputData.getString(KEY_ACTION) ?: ""

    return withContext(Dispatchers.IO) {
      return@withContext try {

        val output = handleFetchAction(type = action, payload = payload)
        val outputData = workDataOf(KEY_OUTPUT_DATA to output, KEY_OUTPUT_TYPE to "REMOTE")
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
