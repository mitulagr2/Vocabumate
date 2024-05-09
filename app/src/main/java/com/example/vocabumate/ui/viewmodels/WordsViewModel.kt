package com.example.vocabumate.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.Word
import com.example.vocabumate.data.WordsRepository
import com.example.vocabumate.data.local.UserPreferences
import com.example.vocabumate.data.local.UserPreferencesRepository
import com.example.vocabumate.ui.screens.WordDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WordsViewModel(
  savedStateHandle: SavedStateHandle,
  private val wordsRepository: WordsRepository,
  private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }

  private val wordArg: String? = savedStateHandle[WordDetailsDestination.wordArg]

  val allLocalWordsState: StateFlow<List<Word>> =
    wordsRepository.getAllWordsStream()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf()
      )

  val preferencesState: StateFlow<UserPreferences> =
    userPreferencesRepository.userPreferencesFlow
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = UserPreferences()
      )

  val loadingWord = Word(wordArg ?: "Daily word", "Loading")
  val errorWord = Word("Error", "Please try again in 30 seconds.")

  var isSaved: Boolean by mutableStateOf(false)

  suspend fun initReviseSet() {
    userPreferencesRepository.updateReviseSet(allLocalWordsState.value, "")
  }

  suspend fun updateReviseSet(wordToChange: String) {
    userPreferencesRepository.updateReviseSet(allLocalWordsState.value, wordToChange)
  }

  suspend fun getDaily(): String {
    return try {
      val newWord = wordsRepository.getRemoteDaily()
      userPreferencesRepository.run { updateDailyWord(newWord) }
      newWord
    } catch (e: Exception) {
      Json.encodeToString(errorWord)
    }
  }

  suspend fun updateDailyStreak() {
    userPreferencesRepository.updateDailyStreak()
  }

  suspend fun getWord(): String? = if (!wordArg.isNullOrEmpty()) {
    val res = wordsRepository.getWord(wordArg)
    if (res !== null) {
      Json.encodeToString(res)
    } else {
      try {
        wordsRepository.getRemoteWord(wordArg)
      } catch (e: Exception) {
        null
      }
    }
  } else null

  suspend fun saveWord(word: Word) {
    isSaved = true
    wordsRepository.insertWord(word)
  }

  suspend fun deleteWord(word: Word) {
    isSaved = false
    wordsRepository.deleteWord(word)
  }
}
