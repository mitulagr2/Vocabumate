package com.example.vocabumate.data

import android.content.Context

interface AppContainer {
  val workManagerWordsRepository: WordsRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

  override val workManagerWordsRepository: WordsRepository by lazy {
    WorkManagerWordsRepository(context)
  }
}

