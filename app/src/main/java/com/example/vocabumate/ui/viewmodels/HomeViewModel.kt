package com.example.vocabumate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.Word
import com.example.vocabumate.data.WordsRepository
import com.example.vocabumate.data.local.UserPreferences
import com.example.vocabumate.data.local.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
  workManagerWordsRepository: WordsRepository,
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

  suspend fun updateReviseSet(wordToChange: String) {
    userPreferencesRepository.updateReviseSet(allLocalWordsState.value, wordToChange)
  }

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }
}
