package com.example.vocabumate.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.vocabumate.GET_DAILY_WORK_NAME
import com.example.vocabumate.GET_WORD_WORK_NAME
import com.example.vocabumate.KEY_ACTION
import com.example.vocabumate.KEY_PAYLOAD
import com.example.vocabumate.MODIFY_WORD_WORK_NAME
import com.example.vocabumate.TAG_DAILY
import com.example.vocabumate.TAG_OUTPUT
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.UserPreferencesRepository
import com.example.vocabumate.data.local.VocabumateDatabase
import com.example.vocabumate.data.local.WordDao
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.workers.FetchWorker
import com.example.vocabumate.workers.RoomWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

interface WordsRepository : LocalWordsRepository, RemoteWordsRepository

class WorkManagerWordsRepository(context: Context) : WordsRepository {

  private val workManager = WorkManager.getInstance(context)
  private val wordDao: WordDao = VocabumateDatabase.getDatabase(context).wordDao()

  override fun getAllWordsStream(): Flow<List<Word>> = wordDao.getAllWords()

  override val outputWorkInfo: Flow<WorkInfo> =
    workManager.getWorkInfosByTagLiveData(TAG_OUTPUT).asFlow().mapNotNull {
      if (it.isNotEmpty()) {
        val roomInfo =
          it.find { it -> it.tags.contains("com.example.vocabumate.workers.RoomWorker") }
        when (roomInfo!!.state) {
          WorkInfo.State.FAILED -> roomInfo
          else -> it.find { it -> it.tags.contains("com.example.vocabumate.workers.FetchWorker") }
        }
      } else null
    }

  override val dailyWordInfo: Flow<WorkInfo> =
    workManager.getWorkInfosByTagLiveData(TAG_DAILY).asFlow().mapNotNull {
      if (it.isNotEmpty()) {
        it.first()
      } else null
    }

  override fun getWord(word: String) {
    val roomBuilder = OneTimeWorkRequestBuilder<RoomWorker>()
      .addTag(TAG_OUTPUT)
      .setInputData(createInputDataForWorkRequest(word, "GET"))

    var continuation = workManager
      .beginUniqueWork(
        GET_WORD_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        roomBuilder.build()
      )

    val remoteConstraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val fetchBuilder = OneTimeWorkRequestBuilder<FetchWorker>()
      .addTag(TAG_OUTPUT)
      .setConstraints(remoteConstraints)
      .setInputData(createInputDataForWorkRequest(word, "GET"))

    continuation = continuation.then(fetchBuilder.build())

    workManager.pruneWork()
    continuation.enqueue()
  }

  override fun insertWord(word: Word) {
    val roomBuilder = OneTimeWorkRequestBuilder<RoomWorker>()
      .setInputData(createInputDataForWorkRequest(Json.encodeToString(word), "INSERT"))

    val continuation = workManager
      .beginUniqueWork(
        MODIFY_WORD_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        roomBuilder.build()
      )

    continuation.enqueue()
  }

  override fun deleteWord(word: Word) {
    val roomBuilder = OneTimeWorkRequestBuilder<RoomWorker>()
      .setInputData(createInputDataForWorkRequest(Json.encodeToString(word), "DELETE"))

    val continuation = workManager
      .beginUniqueWork(
        MODIFY_WORD_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        roomBuilder.build()
      )

    continuation.enqueue()
  }

  init {
    val remoteConstraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val fetchBuilder = PeriodicWorkRequestBuilder<FetchWorker>(24, TimeUnit.HOURS)
      .addTag(TAG_DAILY)
      .setConstraints(remoteConstraints)
      .setInputData(createInputDataForWorkRequest("", "DAILY"))

    workManager
      .enqueueUniquePeriodicWork(
        GET_DAILY_WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        fetchBuilder.build()
      )
  }

  private fun createInputDataForWorkRequest(payload: String, action: String): Data {
    val builder = Data.Builder()
    builder.putString(KEY_PAYLOAD, payload).putString(KEY_ACTION, action)
    return builder.build()
  }
}
