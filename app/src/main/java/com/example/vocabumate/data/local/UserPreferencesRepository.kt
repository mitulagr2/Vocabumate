package com.example.vocabumate.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.vocabumate.data.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val USER_PREFERENCES_NAME = "user_preferences"
private const val KEY_PREFERENCES_REVISE_LIST = "revise_list"
private const val KEY_PREFERENCES_DAILY_STREAK = "daily_streak"
private const val KEY_PREFERENCES_DAILY_WORD = "daily_word"

data class UserPreferences(
  val reviseList: List<String>,
  val dailyStreak: Int,
  val dailyWord: Word
)

/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

  private object PreferencesKeys {
    val REVISE_LIST = booleanPreferencesKey(KEY_PREFERENCES_REVISE_LIST)
    val DAILY_STREAK = stringPreferencesKey(KEY_PREFERENCES_DAILY_STREAK)
    val DAILY_WORD = stringPreferencesKey(KEY_PREFERENCES_DAILY_WORD)
  }

}
