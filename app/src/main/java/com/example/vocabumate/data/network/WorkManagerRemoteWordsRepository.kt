package com.example.vocabumate.data.network

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.vocabumate.FETCH_WORD_WORK_NAME
import com.example.vocabumate.KEY_API_SERVICE
import com.example.vocabumate.KEY_WORD_QUERY
import com.example.vocabumate.TAG_OUTPUT
import com.example.vocabumate.workers.FetchWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class WorkManagerRemoteWordsRepository(
  context: Context
) : RemoteWordsRepository {
  private val baseUrl =
    "https://vocabumate.onrender.com"

  private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

  private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
//    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()

  private val retrofitService: WordApiService by lazy {
    retrofit.create(WordApiService::class.java)
  }

  private val workManager = WorkManager.getInstance(context)

  override val outputWorkInfo: Flow<WorkInfo> =
    workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow().mapNotNull {
      if (it.isNotEmpty()) it.first() else null
    }

  override fun getDefinition(word: String) {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val fetchBuilder = OneTimeWorkRequestBuilder<FetchWorker>()
      .addTag(TAG_OUTPUT)
      .setConstraints(constraints)
      .setInputData(createInputDataForWorkRequest(word))

    val continuation = workManager
      .beginUniqueWork(
        FETCH_WORD_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        fetchBuilder.build()
      )

    continuation.enqueue()
  }

  /**
   * Creates the input data bundle
   */
  private fun createInputDataForWorkRequest(word: String): Data {
    val builder = Data.Builder()
    builder.putAll(workDataOf(KEY_API_SERVICE to retrofitService, KEY_WORD_QUERY to word))
    return builder.build()
  }
}
