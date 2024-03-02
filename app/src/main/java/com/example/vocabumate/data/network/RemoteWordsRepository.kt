package com.example.vocabumate.data.network

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface RemoteWordsRepository {
  val outputWorkInfo: Flow<WorkInfo>
  fun getDefinition(word: String)
}
