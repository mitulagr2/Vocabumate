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
import com.example.vocabumate.data.network.WordRemote
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
//    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()

  private val retrofitService: WordApiService by lazy {
    retrofit.create(WordApiService::class.java)
  }

  override suspend fun doWork(): Result {
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

        val output = retrofitService.getDefinition(wordQuery)
        val outputData = workDataOf(KEY_QUERY_OUTPUT to output.meaning)
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
