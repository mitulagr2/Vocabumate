package com.example.vocabumate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.local.Word
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all words in the Room database.
 */
class LikesViewModel(localWordsRepository: LocalWordsRepository) : ViewModel() {
  val likesUiState: StateFlow<LikesUiState> =
    localWordsRepository.getAllWordsStream().map { LikesUiState(it) }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = LikesUiState()
      )
  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }
}

/**
 * Ui State for HomeScreen
 */
data class LikesUiState(val wordList: List<Word> = listOf())
