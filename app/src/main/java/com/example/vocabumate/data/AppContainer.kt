package com.example.vocabumate.data

import android.content.Context
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.OfflineWordsRepository
import com.example.vocabumate.data.local.VocabumateDatabase
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.data.network.WorkManagerRemoteWordsRepository


interface AppContainer {
  val localWordsRepository: LocalWordsRepository
  val workManagerRemoteWordsRepository: RemoteWordsRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

  override val localWordsRepository: LocalWordsRepository by lazy {
    OfflineWordsRepository(VocabumateDatabase.getDatabase(context).wordDao())
  }

  override val workManagerRemoteWordsRepository: RemoteWordsRepository by lazy {
    WorkManagerRemoteWordsRepository(context)
  }
}

