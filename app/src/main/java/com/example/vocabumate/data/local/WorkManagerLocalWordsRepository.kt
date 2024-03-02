package com.example.vocabumate.data.local

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
import com.example.vocabumate.KEY_ACTION_PAYLOAD
import com.example.vocabumate.KEY_ACTION_TYPE
import com.example.vocabumate.KEY_WORD_QUERY
import com.example.vocabumate.TAG_LOCAL_OUTPUT
import com.example.vocabumate.TAG_REMOTE_OUTPUT
import com.example.vocabumate.data.Word
import com.example.vocabumate.workers.RoomWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WorkManagerLocalWordsRepository(context: Context) : LocalWordsRepository {

  private val workManager = WorkManager.getInstance(context)
  private val wordDao: WordDao = VocabumateDatabase.getDatabase(context).wordDao()

  override val outputWorkInfo: Flow<WorkInfo> =
    workManager.getWorkInfosByTagLiveData(TAG_LOCAL_OUTPUT).asFlow().mapNotNull {
      if (it.isNotEmpty()) Log.d("local", it.first().toString())
      if (it.isNotEmpty()) it.first() else null
    }

//  [WorkInfo{id='46891148-fec5-456a-8e45-827f7ca8e228', state=SUCCEEDED, outputData=Data {KEY_QUERY_OUTPUT : {"word":"tremendous","meaning":"**Tremendous**\n\n**Pronunciation:** /trəˈmendəs/\n\n**Definition:**\n\n* Extremely large, impressive, or significant.\n* Causing awe, fear, or wonder.\n\n**Etymology:**\n\n* From Latin \"tremendus,\" meaning \"to tremble at\" or \"to be greatly afraid.\"\n\n**Usage:**\n\n**1. To describe something impressive or imposing:**\n\n* \"The view from the mountaintop was tremendous.\"\n* \"He was a tremendous athlete with incredible strength.\"\n\n**2. To convey awe or amazement:**\n\n* \"I was filled with tremendous awe when I saw the Great Pyramid.\"\n* \"The earthquake caused tremendous devastation.\"\n\n**3. As a hyperbole to emphasize:**\n\n* \"He was tremendously excited about the news.\"\n* \"It was a tremendous undertaking that took years to complete.\"\n\n**Synonyms:**\n\n* Enormous\n* Immense\n* Vast\n* Colossal\n* Staggering\n\n**Antonyms:**\n\n* Small\n* Insignificant\n* Trivial\n* Minor\n* Tame\n\n**Examples:**\n\n* The city's skyline boasted tremendously tall skyscrapers.\n* The storm unleashed tremendous winds and rainfall.\n* The discovery of life on Mars would have tremendous implications for humanity.\n* The task of organizing the event seemed tremendous at first, but the team worked together and accomplished it with ease.\n* Her tremendous courage and resilience inspired everyone who knew her.","popularity":4}, }, tags=[LOCAL_OUTPUT, com.example.vocabumate.workers.RoomWorker], progress=Data {}, runAttemptCount=1, generation=0, constraints=Constraints{requiredNetworkType=NOT_REQUIRED, requiresCharging=false, requiresDeviceIdle=false, requiresBatteryNotLow=false, requiresStorageNotLow=false, contentTriggerUpdateDelayMillis=-1, contentTriggerMaxDelayMillis=-1, contentUriTriggers=[], }, initialDelayMillis=0, periodicityInfo=null, nextScheduleTimeMillis=9223372036854775807}, stopReason=-256, WorkInfo{id='4deeb389-6832-4bd2-a906-d2ccc14e79e4', state=RUNNING, outputData=Data {}, tags=[LOCAL_OUTPUT, com.example.vocabumate.workers.RoomWorker], progress=Data {}, runAttemptCount=1, generation=0, constraints=Constraints{requiredNetworkType=NOT_REQUIRED, requiresCharging=false, requiresDeviceIdle=false, requiresBatteryNotLow=false, requiresStorageNotLow=false, contentTriggerUpdateDelayMillis=-1, contentTriggerMaxDelayMillis=-1, contentUriTriggers=[], }, initialDelayMillis=0, periodicityInfo=null, nextScheduleTimeMillis=9223372036854775807}, stopReason=-256, WorkInfo{id='a12756d3-a7f1-46bb-8bd0-da644edbc964', state=SUCCEEDED, outputData=Data {KEY_QUERY_OUTPUT : {"word":"tremendous","meaning":"**Tremendous**\n\n**Pronunciation:** /trəˈmendəs/\n\n**Definition:**\n\n* Extremely large, impressive, or significant.\n* Causing awe, fear, or wonder.\n\n**Etymology:**\n\n* From Latin \"tremendus,\" meaning \"to tremble at\" or \"to be greatly afraid.\"\n\n**Usage:**\n\n**1. To describe something impressive or imposing:**\n\n* \"The view from the mountaintop was tremendous.\"\n* \"He was a tremendous athlete with incredible strength.\"\n\n**2. To convey awe or amazement:**\n\n* \"I was filled with tremendous awe when I saw the Great Pyramid.\"\n* \"The earthquake caused tremendous devastation.\"\n\n**3. As a hyperbole to emphasize:**\n\n* \"He was tremendously excited about the news.\"\n* \"It was a tremendous undertaking that took years to complete.\"\n\n**Synonyms:**\n\n* Enormous\n* Immense\n* Vast\n* Colossal\n* Staggering\n\n**Antonyms:**\n\n* Small\n* Insignificant\n* Trivial\n* Minor\n* Tame\n\n**Examples:**\n\n* The city's skyline boasted tremendously tall skyscrapers.\n* The storm unleashed tremendous winds and rainfall.\n* The discovery of life on Mars would have tremendous implications for humanity.\n* The task of organizing the event seemed tremendous at first, but the team worked together and accomplished it with ease.\n* Her tremendous courage and resilience inspired everyone who knew her.","popularity":4}, }, tags=[LOCAL_OUTPUT, com.example.

  override fun getAllWordsStream(): Flow<List<Word>> = wordDao.getAllWords()

  override fun getWord(word: String) {
    val roomBuilder = OneTimeWorkRequestBuilder<RoomWorker>()
      .addTag(TAG_LOCAL_OUTPUT)
      .setInputData(createInputDataForWorkRequest("GET", word))

    val continuation = workManager
      .beginUniqueWork(
        FETCH_WORD_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        roomBuilder.build()
      )

    Log.d("worker", "calling")
    continuation.enqueue()
  }

  override fun insertWord(word: Word) {
    val roomBuilder = OneTimeWorkRequestBuilder<RoomWorker>()
      .addTag(TAG_LOCAL_OUTPUT)
      .setInputData(createInputDataForWorkRequest("INSERT", Json.encodeToString(word)))

    val continuation = workManager
      .beginUniqueWork(
        "INSERT_LOCAL",
        ExistingWorkPolicy.REPLACE,
        roomBuilder.build()
      )

    continuation.enqueue()
  }

  override fun deleteWord(word: Word) {
    val roomBuilder = OneTimeWorkRequestBuilder<RoomWorker>()
      .addTag(TAG_LOCAL_OUTPUT)
      .setInputData(createInputDataForWorkRequest("DELETE", Json.encodeToString(word)))

    val continuation = workManager
      .beginUniqueWork(
        "DELETE_LOCAL",
        ExistingWorkPolicy.REPLACE,
        roomBuilder.build()
      )

    continuation.enqueue()
  }

  private fun createInputDataForWorkRequest(action: String, word: String): Data {
    val builder = Data.Builder()
    builder.putString(KEY_ACTION_TYPE, action).putString(KEY_ACTION_PAYLOAD, word)
    return builder.build()
  }
}
