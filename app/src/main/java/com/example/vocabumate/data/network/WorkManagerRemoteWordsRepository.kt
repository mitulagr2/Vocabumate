package com.example.vocabumate.data.network

import android.content.Context
import android.util.Log
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.vocabumate.FETCH_WORD_WORK_NAME
import com.example.vocabumate.KEY_WORD_QUERY
import com.example.vocabumate.TAG_REMOTE_OUTPUT
import com.example.vocabumate.workers.FetchWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class WorkManagerRemoteWordsRepository(
  context: Context
) : RemoteWordsRepository {

  private val workManager = WorkManager.getInstance(context)

  override val outputWorkInfo: Flow<WorkInfo> =
    workManager.getWorkInfosByTagLiveData(TAG_REMOTE_OUTPUT).asFlow().mapNotNull {
//      if (it.isNotEmpty()) Log.d("local", it.first().toString())
      if (it.isNotEmpty()) it.first() else null
    }

  override fun getDefinition(word: String) {
    Log.d("here", word)
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val fetchBuilder = OneTimeWorkRequestBuilder<FetchWorker>()
      .addTag(TAG_REMOTE_OUTPUT)
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
    builder.putString(KEY_WORD_QUERY, word)
    return builder.build()
  }
}
