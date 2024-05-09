package com.example.vocabumate.data.network

import androidx.work.WorkInfo
import kotlinx.coroutines.flow.Flow

interface RemoteWordsRepository {
  suspend fun getRemoteWord(word: String): String

  suspend fun getRemoteDaily(): String
}
