package com.example.vocabumate.data

import android.content.Context
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.WorkManagerLocalWordsRepository
import com.example.vocabumate.data.network.RemoteWordsRepository
import com.example.vocabumate.data.network.WorkManagerRemoteWordsRepository


interface AppContainer {
  val workManagerLocalWordsRepository: LocalWordsRepository
  val workManagerRemoteWordsRepository: RemoteWordsRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

  override val workManagerLocalWordsRepository: LocalWordsRepository by lazy {
    WorkManagerLocalWordsRepository(context)
  }

  override val workManagerRemoteWordsRepository: RemoteWordsRepository by lazy {
    WorkManagerRemoteWordsRepository(context)
  }
}

