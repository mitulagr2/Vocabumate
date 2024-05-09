package com.example.vocabumate.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.vocabumate.data.local.USER_PREFERENCES_NAME
import com.example.vocabumate.data.local.UserPreferencesRepository

interface AppContainer {
  val wordsRepository: WordsRepository
  val userPreferencesRepository: UserPreferencesRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

  override val wordsRepository: WordsRepository by lazy {
    WordsRepository(context)
  }

  override val userPreferencesRepository: UserPreferencesRepository by lazy {
    UserPreferencesRepository(context.dataStore)
  }

  override val userPreferencesRepository: UserPreferencesRepository by lazy {
    UserPreferencesRepository(context.dataStore)
  }
}

val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)
