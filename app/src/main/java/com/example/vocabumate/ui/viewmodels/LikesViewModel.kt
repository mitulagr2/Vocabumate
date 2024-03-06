package com.example.vocabumate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.Word
import com.example.vocabumate.data.WordsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all words in the Room database.
 */
class LikesViewModel(
  private val workManagerWordsRepository: WordsRepository
) : ViewModel() {

  val allLocalWordsState: StateFlow<List<Word>> =
    workManagerWordsRepository.getAllWordsStream()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf()
      )

  fun deleteWord(word: Word) {
    workManagerWordsRepository.deleteWord(word)
  }

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }
}
