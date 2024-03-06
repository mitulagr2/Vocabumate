package com.example.vocabumate.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.Word
import com.example.vocabumate.data.WordsRepository
import com.example.vocabumate.data.local.UserPreferences
import com.example.vocabumate.data.local.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json

class DailyViewModel(
  private val workManagerWordsRepository: WordsRepository,
  private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

  val allLocalWordsState: StateFlow<List<Word>> =
    workManagerWordsRepository.getAllWordsStream()
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

  var isSaved: Boolean by mutableStateOf(false)
    private set

  fun saveWord() {
    isSaved = true
    workManagerWordsRepository.insertWord(Json.decodeFromString(preferencesState.value.dailyWord))
  }

  fun deleteWord() {
    isSaved = false
    workManagerWordsRepository.deleteWord(Json.decodeFromString(preferencesState.value.dailyWord))
  }

  suspend fun initReviseSet() {
    userPreferencesRepository.updateReviseSet(allLocalWordsState.value, "")
  }

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }
}
