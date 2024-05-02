package com.example.vocabumate.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.vocabumate.data.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException

const val USER_PREFERENCES_NAME = "user_preferences"
private const val KEY_PREFERENCES_REVISE_SET = "revise_set"
private const val KEY_PREFERENCES_DAILY_STREAK = "daily_streak"
private const val KEY_PREFERENCES_LAST_TIME = "last_time"
private const val KEY_PREFERENCES_DAILY_WORD = "daily_word"

data class UserPreferences(
  val reviseSet: Set<String> = setOf(),
  val dailyStreak: Int = 0,
  val lastTime: Int = 0,
  val dailyWord: String = Json.encodeToString(Word("", ""))
)

/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

  object PreferencesKeys {
    val REVISE_SET = stringSetPreferencesKey(KEY_PREFERENCES_REVISE_SET)
    val DAILY_STREAK = intPreferencesKey(KEY_PREFERENCES_DAILY_STREAK)
    val LAST_TIME = intPreferencesKey(KEY_PREFERENCES_LAST_TIME)
    val DAILY_WORD = stringPreferencesKey(KEY_PREFERENCES_DAILY_WORD)
  }

  val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
    .catch { exception ->
      if (exception is IOException) {
        emit(emptyPreferences())
      } else {
        throw exception
      }
    }
    .map { preferences ->
      val reviseSet: Set<String> = preferences[PreferencesKeys.REVISE_SET] ?: setOf()
      val dailyStreak: Int = preferences[PreferencesKeys.DAILY_STREAK] ?: 0
      val lastTime: Int = preferences[PreferencesKeys.LAST_TIME] ?: 1
      val dailyWord: String =
        preferences[PreferencesKeys.DAILY_WORD] ?: Json.encodeToString(Word("", ""))

      UserPreferences(reviseSet, dailyStreak, lastTime, dailyWord)
    }

  suspend fun updateReviseSet(localList: List<Word>, wordToChange: String) {
    dataStore.edit { preferences ->
      var newSet = preferences[PreferencesKeys.REVISE_SET]

      if (newSet.isNullOrEmpty() || wordToChange.isEmpty())
        newSet = localList.slice(0..2)
          .map { Json.encodeToString(it) }.toSet()

      if (wordToChange.isNotEmpty() && localList.size > 3) {
        val wordList = newSet.map { Json.decodeFromString<Word>(it) }
        val wordStrSet = wordList.map { it.word }.toSet()
        val newWords = localList.filterNot { it.word in wordStrSet }
        newSet = (wordList.filterNot { it.word == wordToChange } + newWords.random()).map {
          Json.encodeToString(it)
        }.toSet()
      }

      preferences[PreferencesKeys.REVISE_SET] = newSet
    }
  }

  suspend fun updateDailyStreak() {
    dataStore.edit { preferences ->
      val prev = preferences[PreferencesKeys.LAST_TIME] ?: 1
      val today = (System.currentTimeMillis() / (1000 * 3600 * 24)).toInt()
      preferences[PreferencesKeys.LAST_TIME] = today

      if (today - prev == 1) {
        val curStreak = preferences[PreferencesKeys.DAILY_STREAK] ?: 0
        preferences[PreferencesKeys.DAILY_STREAK] = curStreak + 1
      } else if (today - prev > 1) {
        preferences[PreferencesKeys.DAILY_STREAK] = 1
      }
    }
  }

  suspend fun updateDailyWord(newDaily: String) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.DAILY_WORD] = newDaily
      val today = (System.currentTimeMillis() / (1000 * 3600 * 24)).toInt()
      preferences[PreferencesKeys.LAST_TIME] = today
    }
  }
}
