package com.example.vocabumate.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.vocabumate.data.local.USER_PREFERENCES_NAME
import com.example.vocabumate.data.local.UserPreferencesRepository

interface AppContainer {
  val workManagerWordsRepository: WordsRepository
  val userPreferencesRepository: UserPreferencesRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

  override val workManagerWordsRepository: WordsRepository by lazy {
    WorkManagerWordsRepository(context)
  }

  override val userPreferencesRepository: UserPreferencesRepository by lazy {
    UserPreferencesRepository(context.dataStore)
  }
}

private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)
