package com.example.vocabumate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vocabumate.data.local.LocalWordsRepository
import com.example.vocabumate.data.Word
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all words in the Room database.
 */
class LikesViewModel(workManagerLocalWordsRepository: LocalWordsRepository) : ViewModel() {

  val allLocalWordsState: StateFlow<List<Word>> =
    workManagerLocalWordsRepository.getAllWordsStream()
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = listOf()
      )

  companion object {
    private const val TIMEOUT_MILLIS = 5_000L
  }
}
